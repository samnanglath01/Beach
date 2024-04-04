package com.example.beachtest

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class DiningHallDetailsFragment : Fragment() {

    private lateinit var reviewEditText: EditText
    private lateinit var submitReviewButton: Button
    private lateinit var reviewImageView: ImageView
    private lateinit var reviewRatingBar: RatingBar

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            reviewImageView.setImageURI(uri)
            reviewImageView.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dining_hall_details, container, false)

        reviewRatingBar = view.findViewById(R.id.reviewRatingBar)
        reviewEditText = view.findViewById(R.id.reviewEditText)
        submitReviewButton = view.findViewById(R.id.submitReviewButton)
        reviewImageView = view.findViewById(R.id.reviewImageView)

        submitReviewButton.setOnClickListener {
            reviewRatingBar.rating = 0f
            reviewEditText.text.clear()
            reviewImageView.visibility = View.GONE
        }

        val addPhotoButton: Button = view.findViewById(R.id.addPhotoButton)
        addPhotoButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        return view
    }
}