package com.example.beachtest

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentMealTimeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.DayOfWeek
import java.time.LocalDate

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMealTimeButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupMealTimeButtons() {
        val today = LocalDate.now().dayOfWeek

        binding.breakfastButton.setOnClickListener {
            if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY) {
                Toast.makeText(context, "Breakfast is not served on weekends. Please select another meal time.", Toast.LENGTH_LONG).show()
            } else {
                saveMealTimeChoice("Breakfast")
                Toast.makeText(context, "Breakfast selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.brunchButton.setOnClickListener {
            if (today != DayOfWeek.SATURDAY && today != DayOfWeek.SUNDAY) {
                Toast.makeText(context, "Brunch is only served on weekends. Please select another meal time.", Toast.LENGTH_LONG).show()
            } else {
                saveMealTimeChoice("Brunch")
                Toast.makeText(context, "Brunch selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.lunchButton.setOnClickListener {
            if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY) {
                Toast.makeText(context, "Lunch is not served on weekends. Please select another meal time.", Toast.LENGTH_LONG).show()
            } else {
                saveMealTimeChoice("Lunch")
                Toast.makeText(context, "Lunch selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.dinnerButton.setOnClickListener {
            Toast.makeText(context, "Dinner selected", Toast.LENGTH_SHORT).show()
            saveMealTimeChoice("Dinner")
        }
        binding.backToDiningHallButton.setOnClickListener {
            navigateBackToDiningHall()
        }
    }

    private fun saveMealTimeChoice(mealTime: String) {
        val userUid = auth.currentUser?.uid
        if (userUid != null) {
            // User is logged in, update Firestore
            firestore.collection("Users").document(userUid)
                .update("mealTime", mealTime)
                .addOnSuccessListener {
                    navigateToMenuItems()
                }
                .addOnFailureListener { e ->
                    Log.e("MealTimeFragment", "Failed to update meal time", e)
                    Toast.makeText(context, "Error updating meal time. Please try again.", Toast.LENGTH_LONG).show()
                }
        } else {
            // No user is logged in, handle as guest
            Toast.makeText(context, "$mealTime selected in guest mode", Toast.LENGTH_SHORT).show()
            navigateToMenuItems()
        }
    }

    private fun navigateToMenuItems() {
        // Check if the current destination is correct before navigating
        if (isAdded && findNavController().currentDestination?.id == R.id.mealTimeFragment) {
            findNavController().navigate(R.id.action_mealTimeFragment_to_menuItemsFragment)
        }
    }

    private fun navigateBackToDiningHall() {
        // Check if the current destination is correct before navigating
        if (isAdded && findNavController().currentDestination?.id == R.id.mealTimeFragment) {
            findNavController().navigate(R.id.action_mealTimeFragment_to_homeFragment)
        }
    }

}