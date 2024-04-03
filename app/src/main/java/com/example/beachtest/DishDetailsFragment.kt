package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageButton
import com.google.firebase.firestore.FirebaseFirestore

class DishDetailsFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dish_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()

        // Get the dish name passed to the fragment
        val dishName = arguments?.getString("dishName") ?: "Unknown Dish"

        // Set dish name dynamically
        view.findViewById<TextView>(R.id.dishNameTextView).text = dishName

        // Fetch dish details
        fetchDishDetails(dishName, view)

        // Setup thumbs up/down listeners
        view.findViewById<ImageButton>(R.id.thumbsUpButton).setOnClickListener {
            Toast.makeText(context, "You liked $dishName", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<ImageButton>(R.id.thumbsDownButton).setOnClickListener {
            Toast.makeText(context, "You disliked $dishName", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchDishDetails(dishName: String, view: View) {
        firestore.collection("menu").document(dishName).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val ingredientsList = document["ingredients"] as? List<String> ?: listOf()
                val formattedIngredients = ingredientsList.joinToString("\n") { "• $it" }
                view.findViewById<TextView>(R.id.ingredientsTextView).text = formattedIngredients
            } else {
                Toast.makeText(context, "Dish details not found.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, "Error fetching dish details: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
