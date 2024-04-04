package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ImageButton

class DishDetailsFragment : Fragment() {

    // Database helper instance
    private lateinit var dbHelper: IngredientsDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database helper
        dbHelper = IngredientsDatabaseHelper(requireContext())

        // Example dish name
        val dishName = "Spaghetti Carbonara"

        // Fetch ingredients from the database
        val ingredients = dbHelper.getIngredientsForDish(dishName)

        // Set dish name
        view.findViewById<TextView>(R.id.dishNameTextView).text = dishName

        // Format ingredients list into a bulleted list
        val formattedIngredients = ingredients.joinToString("\n") { "• $it" }

        // Set ingredients text
        view.findViewById<TextView>(R.id.ingredientsTextView).text = formattedIngredients

        // Setup thumbs up/down listeners
        // Note: Replace Button with ImageButton if you are using ImageButtons in your layout
        view.findViewById<ImageButton>(R.id.thumbsUpButton).setOnClickListener {
            Toast.makeText(context, "You liked $dishName", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<ImageButton>(R.id.thumbsDownButton).setOnClickListener {
            Toast.makeText(context, "You disliked $dishName", Toast.LENGTH_SHORT).show()
        }
    }
}
