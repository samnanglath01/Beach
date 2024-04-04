package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment() {
    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater, container, false)

        // Set up click listeners using View Binding
        binding.buttonProfile.setOnClickListener {
            Toast.makeText(context, "Profile clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_profileFragment)
        }

        // This will navigate the user to photography tips
        binding.buttonPhotographyTips.setOnClickListener {
            Toast.makeText(context, "Photography Tips clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_photographyTipFragment)
        }

        // This will navigate the user to the prices and meal rates
        binding.buttonPrices.setOnClickListener {
            Toast.makeText(context, "Prices clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_pricesFragment)
        }

        // This will navigate the user to the dining hall hours
        binding.buttonHours.setOnClickListener {
            Toast.makeText(context, "Hours clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_hallHoursFragment)
        }

        // Luis Flores
        // This will navigate the user to the virtual tour for each dining hall
        binding.buttonVirtualTour.setOnClickListener{
            Toast.makeText(context, "Virtual Tour clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_virtualTourFragment)
        }

        // Luis Flores
        // This will navigate the user to the food pantry page and info/pictures for any drops
        binding.buttonPantry.setOnClickListener {
            Toast.makeText(context, "Food Pantry clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_foodPantryFragment)
        }

        // This will navigate the user to the food trucks/vendors on campus
        binding.buttonVendors.setOnClickListener {
            Toast.makeText(context, "Campus Vendors clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_homePageFragment_to_foodTrucksFragment)
        }

        return binding.root
    }
}
