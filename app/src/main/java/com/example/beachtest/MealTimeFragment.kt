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
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentMealTimeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.time.DayOfWeek
import java.time.LocalDate

// Luis Flores
// Define a Fragment subclass for managing meal time selection
class MealTimeFragment : Fragment() {
    private lateinit var binding: FragmentMealTimeBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentMealTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMealTimeButtons()
        setupRatingBar()

        // Retrieve the selected dining hall from arguments
        val selectedDiningHall = arguments?.getString("selectedDiningHall")

        // Call setupSubmitReviewButton with the selected dining hall
        selectedDiningHall?.let { diningHall ->
            setupSubmitReviewButton(diningHall)
        }
    }

    // Function to set up meal time buttons and their click listeners
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupMealTimeButtons() {
        val today = LocalDate.now().dayOfWeek

        // Define click listeners for each meal time button
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

        // Back button click listener to navigate back to the dining hall selection fragment
        binding.backToDiningHallButton.setOnClickListener {
            navigateBackToDiningHall()
        }
    }

    // Setup for the rating bar
    private fun setupRatingBar() {
        binding.ratingBar.rating = 2.5f
        binding.ratingBar.stepSize = 1.0f
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            // Update the text below the RatingBar based on the selected value
            binding.ratingScaleText.text = when (rating.toInt()) {
                1 -> "Very Bad"
                2 -> "Bad"
                3 -> "Good"
                4 -> "Great"
                5 -> "Awesome"
                else -> ""
            }
            // Optionally show a toast
            Toast.makeText(context, "Rating: $rating", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to save the selected meal time choice to Firestore
    private fun saveMealTimeChoice(mealTime: String) {
        val userUid = auth.currentUser?.uid
        if (userUid != null) {
            // User is logged in, update Firestore with the selected meal time
            firestore.collection("Users").document(userUid)
                .update("mealTime", mealTime)
                .addOnSuccessListener {
                    // Navigate to the menu items fragment upon successful update
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
            findNavController().navigate(R.id.action_mealTimeFragment_to_homePageFragment)
            Toast.makeText(context, "Clicked back to Dining Halls button.", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupSubmitReviewButton(diningHall: String) {
        binding.submitReviewButton.setOnClickListener {
            val reviewText = binding.reviewEditText.text.toString().trim()
            val rating = binding.ratingBar.rating

            Log.d("SubmitReview", "Button clicked with review: $reviewText and rating: $rating")

            if (reviewText.isEmpty()) {
                Toast.makeText(context, "Please write your review", Toast.LENGTH_SHORT).show()
                Log.d("SubmitReview", "Review text is empty")
                return@setOnClickListener
            }

            if (rating == 0f) {
                Toast.makeText(context, "Please select a rating", Toast.LENGTH_SHORT).show()
                Log.d("SubmitReview", "Rating not selected")
                return@setOnClickListener
            }

            if (!isUserLoggedIn()) {
                Toast.makeText(context, "No user logged in. Please log in to submit a review.", Toast.LENGTH_SHORT).show()
                Log.d("SubmitReview", "No user logged in")
                return@setOnClickListener
            }

            saveReview(reviewText, rating, diningHall)
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    // Function to save the review to Firestore
    private fun saveReview(reviewText: String, rating: Float, diningHall: String) {
        // User check is no longer necessary here because it's handled in the button setup.
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        val reviewData = hashMapOf(
            "userId" to userId,
            "reviewText" to reviewText,
            "rating" to rating,
            "diningHall" to diningHall,
            "timestamp" to FieldValue.serverTimestamp()
        )

        firestore.collection("reviews")
            .add(reviewData)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Review written with ID: ${documentReference.id}")
                Toast.makeText(context, "Review saved successfully!", Toast.LENGTH_SHORT).show()
                // Optional: Navigate to another fragment or update UI to reflect the successful operation
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error adding review", e)
                Toast.makeText(context, "Error adding review. Please try again.", Toast.LENGTH_LONG).show()
            }
    }



}