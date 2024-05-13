package com.example.beachtest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentMealTimeBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.time.DayOfWeek
import java.time.LocalDate
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.RatingBar
import android.graphics.Color



// Luis Flores & Sebastian Tadeo
// Define a Fragment subclass for managing meal time selection
class MealTimeFragment : Fragment() {
    private lateinit var binding: FragmentMealTimeBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var guestId: String? = null

    companion object {
        private const val IMAGE_PICK_CODE = 999 // Request code for picking an image
        private const val PERMISSION_CODE = 1001 // Request code for permissions
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
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
        setupRatingBar()
        setupAddPhotoButton()

        // Retrieve the selected dining hall from arguments
        val selectedDiningHall = arguments?.getString("selectedDiningHall")

        // Call setupSubmitReviewButton with the selected dining hall
        selectedDiningHall?.let { diningHall ->
            setupReviewSubmission()
            fetchReviews(diningHall)
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
            saveMealTimeChoice("Dinner")
            Toast.makeText(context, "Dinner selected", Toast.LENGTH_SHORT).show()
        }

        // Back button click listener to navigate back to the dining hall selection fragment
        binding.backToDiningHallButton.setOnClickListener {
            navigateBackToDiningHall()
        }
    }

    // Sebastian Tadeo
    // Setup for the rating bar
    private fun setupRatingBar() {
        binding.ratingBar.rating = 3.0f
        binding.ratingBar.stepSize = 1.0f
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            // Update the text below the RatingBar based on the selected value
            val roundedRating = Math.round(rating).coerceIn(1,5)
            ratingBar.rating = roundedRating.toFloat()
            binding.ratingScaleText.text = when (roundedRating) {
                1 -> "Very Bad"
                2 -> "Bad"
                3 -> "Good"
                4 -> "Great"
                5 -> "Awesome"
                else -> "Not Rated"
            }
            // Optionally show a toast
            Toast.makeText(context, "Rating: $roundedRating", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }



    // Sebastian Tadeo
    // Function to save the selected meal time choice to Firestore
    // Simplified user login check in other parts of code
    private fun saveMealTimeChoice(mealTime: String) {
        if (!isUserLoggedIn()) {
            Toast.makeText(context, "$mealTime selected in guest mode", Toast.LENGTH_SHORT).show()
            navigateToMenuItems()
            return
        }

        val userUid = FirebaseAuth.getInstance().currentUser!!.uid
        firestore.collection("Users").document(userUid)
            .update("mealTime", mealTime)
            .addOnSuccessListener {
                navigateToMenuItems()
            }
            .addOnFailureListener { e ->
                Log.e("MealTimeFragment", "Failed to update meal time", e)
                Toast.makeText(context, "Error updating meal time. Please try again.", Toast.LENGTH_LONG).show()
            }
    }

    private fun navigate(destinationId: Int) {
        if (isAdded && findNavController().currentDestination?.id == R.id.mealTimeFragment) {
            findNavController().navigate(destinationId)
        } else {
            Log.e("NavigationError", "Attempted to navigate to $destinationId but current destination was not MealTimeFragment.")
        }
    }

    // Usage
    private fun navigateBackToDiningHall() {
        navigate(R.id.action_mealTimeFragment_to_homePageFragment)
        Toast.makeText(context, "Clicked back to Dining Halls button.", Toast.LENGTH_LONG).show()
    }

    private fun navigateToMenuItems() {
        navigate(R.id.action_mealTimeFragment_to_menuItemsFragment)
    }

    private fun setupReviewSubmission() {
        binding.submitReviewButton.setOnClickListener {
            val reviewText = binding.reviewEditText.text.toString()
            val rating = binding.ratingBar.rating
            //if (!validateReviewInputs(rating, reviewText)) return@setOnClickListener  // Check input validation
            submitReview(rating, reviewText)
        }
    }

    // Function to save the review to Firestore
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
    private fun handleFirestoreError(e: Exception, action: String) {
        Log.e("FirestoreError", "Error during $action: ${e.message}")
        Toast.makeText(context, "Failed to $action. Please try again.", Toast.LENGTH_LONG).show()
    }

    private fun fetchReviews(diningHall: String) {
        firestore.collection("diningHallReviews")
            .whereEqualTo("diningHall", diningHall)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    addReviewToLayout(document["reviewText"] as String, document["rating"] as Double)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("MealTimeFragment", "Error getting documents: ", exception)
            }
    }

    private fun addReviewToLayout(reviewText: String, rating: Double) {
        val reviewLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also {
                it.topMargin = 16
                it.bottomMargin = 16
            }
            background = ContextCompat.getDrawable(requireContext(), R.drawable.review_background)
            setPadding(16, 16, 16, 16)
        }

        val textView = TextView(context).apply {
            text = reviewText
            textSize = 16f
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val ratingBar = RatingBar(context, null, android.R.attr.ratingBarStyleSmall).apply {
            this.rating = rating.toFloat()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        reviewLayout.addView(textView)
        reviewLayout.addView(ratingBar)
        binding.previousReviewsSection.addView(reviewLayout)
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE)
            }
        } else {
            openGallery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(context, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val selectedImageUri: Uri? = data?.data
            val imageView = view?.findViewById<ImageView>(R.id.reviewImageView)
            imageView?.setImageURI(selectedImageUri)
            imageView?.visibility = View.VISIBLE
        }
    }

    private fun setupAddPhotoButton() {
        binding.addPhotoButton.setOnClickListener {
            checkPermissionAndOpenGallery()
        }


    }
}

