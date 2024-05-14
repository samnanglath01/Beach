package com.example.beachtest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentFoodTrucksBinding

class FoodTrucksFragment : Fragment() {
    private lateinit var binding: FragmentFoodTrucksBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodTrucksBinding.inflate(inflater, container, false)

        // Assuming foodTrucksButton navigates to Food Truck Vendor Details
        binding.foodTrucksButton.setOnClickListener {
            try {
                findNavController().navigate(R.id.actionFoodTrucksFragmentToFoodTruckVendorDetailsFragment)
            } catch (e: Exception) {
                Log.e("FoodTrucksFragment", "Navigation error", e)
                // Optionally show an error message or handle the error gracefully
            }
        }

        // Assuming farmersMarketButton navigates to Farmers Market Vendor Details
        binding.farmersMarketButton.setOnClickListener {
            findNavController().navigate(R.id.farmersMarketVendorDetailsFragment)
        }

        // Navigate back to home page when the "Back to Home Page" button is clicked
        binding.backToOptionButton.setOnClickListener {
            findNavController().navigate(R.id.homePageFragment)
        }

        return binding.root


    }
}