package com.example.beachtest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.fragment.app.Fragment

class DiningHallDetailsFragment : Fragment() {

    // UI components
    private lateinit var reviewEditText: EditText
    private lateinit var submitReviewButton: Button
    private lateinit var reviewImageView: ImageView
    private lateinit var reviewRatingBar: RatingBar

    // Request code for picking an image from the gallery
    private val REQUEST_CODE_PICK_IMAGE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dining_hall_details, container, false)

        // Initialize the RatingBar, EditText, Button, and ImageView
        reviewRatingBar = view.findViewById(R.id.reviewRatingBar)
        reviewEditText = view.findViewById(R.id.reviewEditText)
        submitReviewButton = view.findViewById(R.id.submitReviewButton)
        reviewImageView = view.findViewById(R.id.reviewImageView)

        // Set up the listener for the 'Submit Review' button
        submitReviewButton.setOnClickListener {
            // Here, you would handle the submission of the review and the rating
            // For example, save the review to a database, along with the rating
            // Clear the input fields after submission
            reviewRatingBar.rating = 0f
            reviewEditText.text.clear()
            reviewImageView.visibility = View.GONE
        }

        // Listener for the ImageView to open the gallery
        reviewImageView.setOnClickListener {
            openGalleryForImage()
        }

        return view
    }

    // Function to open the gallery to select an image
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    // Handle the result from the image picker activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data
            // Set the picked image to the ImageView and make it visible
            reviewImageView.setImageURI(selectedImageUri)
            reviewImageView.visibility = View.VISIBLE
        }
    }
}
