package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beachtest.databinding.FragmentDishDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DishDetailsFragment : Fragment() {

    // Firestore instance
    private lateinit var firestore: FirebaseFirestore

    // View binding instance
    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!

    // Using Safe Args to handle navigation arguments
    private val args: DishDetailsFragmentArgs by navArgs()


    // Local variables to track user's like/dislike state
    private var userLikesDish: Boolean? = null
    private var thumbsUpCount = 0
    private var thumbsDownCount = 0
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()

        // Retrieve the dish ID passed via navigation arguments
        val dishId = args.dishId
        binding.dishNameTextView.text = dishId

        // Fetch dish details
        fetchDishDetails(dishId)
        updateThumbsCounters(dishId)
        Toast.makeText(context, "Now displaying $dishId details.", Toast.LENGTH_SHORT).show()

        // Setup thumbs up/down listeners
        setupThumbListeners(dishId)

        // Set up the listener for the back button
        binding.backMenuItemsButton.setOnClickListener {
            Toast.makeText(context, "Clicked on back to menu button.", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun updateThumbsCounters(dishId: String) {
        firestore.collection("menu").document(dishId).get().addOnSuccessListener { document ->
            if (document.exists()) {
                thumbsUpCount = document.getLong("thumbsUpCounter")?.toInt() ?: 0
                thumbsDownCount = document.getLong("thumbsDownCounter")?.toInt() ?: 0
                binding.thumbsUpCounterTextView.text = thumbsUpCount.toString()
                binding.thumbsDownCounterTextView.text = thumbsDownCount.toString()
            }
        }
    }

    private fun setupThumbListeners(dishId: String) {
        binding.thumbsUpButton.setOnClickListener {
            if (auth.currentUser != null) {
                updateThumbsUp(dishId, !(userLikesDish ?: false))
            } else {
                Toast.makeText(context, "Sign in/sign up to be able to vote on meals", Toast.LENGTH_SHORT).show()
            }
        }

        binding.thumbsDownButton.setOnClickListener {
            if (auth.currentUser != null) {
                updateThumbsDown(dishId, userLikesDish ?: true)
            } else {
                Toast.makeText(context, "Sign in/sign up to be able to vote on meals", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateThumbsUp(dishId: String, newStatus: Boolean) {
        if (newStatus) {
            if (userLikesDish == false && thumbsDownCount > 0) {
                thumbsDownCount-- // Decrement dislikes if previously disliked
            }
            if (userLikesDish != true) { // Only increment if previously not liked
                thumbsUpCount++
            }
        } else {
            if (thumbsUpCount > 0) {
                thumbsUpCount-- // Decrement likes if unliking
            }
        }
        userLikesDish = if (newStatus) true else null // Set to true or null if unliking

        // Apply updates to Firestore
        updateFirestore(dishId)
    }

    private fun updateThumbsDown(dishId: String, newStatus: Boolean) {
        if (newStatus) {
            if (userLikesDish == true && thumbsUpCount > 0) {
                thumbsUpCount-- // Decrement likes if previously liked
            }
            if (userLikesDish != false) { // Only increment if previously not disliked
                thumbsDownCount++
            }
        } else {
            if (thumbsDownCount > 0) {
                thumbsDownCount-- // Decrement dislikes if un-disliking
            }
        }
        userLikesDish = if (newStatus) false else null // Set to false or null if un-disliking

        // Apply updates to Firestore
        updateFirestore(dishId)
    }

    private fun updateFirestore(dishId: String) {
        val updates = mapOf(
            "thumbsUpCounter" to thumbsUpCount,
            "thumbsDownCounter" to thumbsDownCount
        )

        firestore.collection("menu").document(dishId)
            .update(updates)
            .addOnSuccessListener {
                binding.thumbsUpCounterTextView.text = thumbsUpCount.toString()
                binding.thumbsDownCounterTextView.text = thumbsDownCount.toString()
                Toast.makeText(context, if (userLikesDish == true) "Liked!" else if (userLikesDish == false) "Disliked!" else "Neutral", Toast.LENGTH_SHORT).show()
            }
    }


    private fun fetchDishDetails(dishId: String) {
        firestore.collection("menu").document(dishId).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val ingredientsList = document["Ingredients"] as? List<*>
                val description = document.getString("Description")

                // Handling possible null or missing fields
                val formattedIngredients = ingredientsList?.joinToString("\n") { "• $it" } ?: "Ingredients not available"
                val dishDescription = description ?: "Description not available"

                // Updating the UI
                binding.ingredientsTextView.text = formattedIngredients
                binding.descriptionTextView.text = dishDescription
            } else {
                Toast.makeText(context, "Dish details not found.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, "Error fetching dish details: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding when the view is destroyed
        _binding = null
    }
}