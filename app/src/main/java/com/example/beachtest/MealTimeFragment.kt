package com.example.beachtest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentMealTimeBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.DayOfWeek
import java.time.LocalDate

class MealTimeFragment : Fragment() {
    private lateinit var binding: FragmentMealTimeBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var guestId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealTimeBinding.inflate(inflater, container, false)
        guestId = loadGuestId() // Load the guest ID from SharedPreferences
        return binding.root
    }

    private fun loadGuestId(): String? {
        val prefs = activity?.getSharedPreferences("GuestPrefs", Context.MODE_PRIVATE)
        return prefs?.getString("guestId", null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMealTimeButtons()
        setupReviewSubmission()
    }


    private fun setupReviewSubmission() {
        binding.submitReviewButton.setOnClickListener {
            val rating = binding.ratingBar.rating
            val reviewText = binding.reviewEditText.text.toString()
            submitReview(rating, reviewText)
        }
    }

    private fun submitReview(rating: Float, reviewText: String) {
        val userUid = auth.currentUser?.uid

        if (userUid != null && reviewText.isNotBlank()) {
            firestore.collection("Users").document(userUid).get()
                .addOnSuccessListener { document ->
                    val diningHall = document.getString("diningHall") ?: "Unknown Dining Hall"
                    val userName = document.getString("username") ?: "No user logged in"
                    val reviewData = hashMapOf(
                        "userId" to userUid,
                        "rating" to rating,
                        "review" to reviewText,
                        "timestamp" to Timestamp.now(),
                        "diningHall" to diningHall,
                        "username" to userName
                    )

                    firestore.collection("reviews")
                        .add(reviewData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                            binding.reviewEditText.text.clear()
                            binding.ratingBar.rating = 0f
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to submit review: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to fetch dining hall information: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Review text cannot be empty.", Toast.LENGTH_SHORT).show()
        }
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
            saveMealTimeChoice("Dinner")
            Toast.makeText(context, "Dinner selected", Toast.LENGTH_SHORT).show();
        }
    }

    private fun saveMealTimeChoice(mealTime: String) {
        val userUid = auth.currentUser?.uid

        if (userUid != null) {
            // Save for logged in user
            firestore.collection("Users").document(userUid)
                .update("mealTime", mealTime)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_mealTimeFragment_to_menuItemsFragment)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to save meal time: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            // Luis Flores, guest mode
        } else if (guestId != null) {
            // Save for guest using the same ID
            firestore.collection("Guests").document(guestId!!)
                .update("mealTime", mealTime)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_mealTimeFragment_to_menuItemsFragment)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to save meal time for guest: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}