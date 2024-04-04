package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import android.util.Log

class FoodTrucksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_trucks, container, false)

        // Assuming foodTrucksButton navigates to Food Truck Vendor Details
        view.findViewById<Button>(R.id.foodTrucksButton).setOnClickListener {
            try {
                findNavController().navigate(R.id.actionFoodTrucksFragmentToFoodTruckVendorDetailsFragment)
            } catch (e: Exception) {
                Log.e("FoodTrucksFragment", "Navigation error", e)
                // Optionally show an error message or handle the error gracefully
            }
        }

        // Assuming farmersMarketButton navigates to Farmers Market Vendor Details
        view.findViewById<Button>(R.id.farmersMarketButton).setOnClickListener {
            findNavController().navigate(R.id.farmersMarketVendorDetailsFragment)
        }

        // Navigate back to home page when the "Back to Home Page" button is clicked
        view.findViewById<Button>(R.id.backToOptionButton).setOnClickListener {
            findNavController().navigate(R.id.action_foodTrucksFragment_to_optionsFragment)
        }

        return view


    }
}
