package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentMealTimeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Luis Flores
class MealTimeFragment : Fragment() {

    private lateinit var binding: FragmentMealTimeBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMealTimeButtons()
    }

    private fun setupMealTimeButtons() {
        binding.breakfastButton.setOnClickListener {
            saveMealTimeChoice("Breakfast")
        }

        binding.brunchButton.setOnClickListener {
            saveMealTimeChoice("Brunch")
        }

        binding.lunchButton.setOnClickListener {
            saveMealTimeChoice("Lunch")
        }

        binding.dinnerButton.setOnClickListener {
            saveMealTimeChoice("Dinner")
        }
    }

    private fun saveMealTimeChoice(mealTime: String) {
        val userUid = auth.currentUser?.uid ?: return // Get current user UID
        // Update the user's document in the 'Users' collection with the meal time choice
        firestore.collection("Users").document(userUid)
            .update("mealTime", mealTime)
            .addOnSuccessListener {
                // Navigate to the MenuItemsFragment after successful update
                findNavController().navigate(R.id.action_mealTimeFragment_to_menuItemsFragment)
            }
            .addOnFailureListener { e ->
                // Handle failure here if needed
            }
    }
}
