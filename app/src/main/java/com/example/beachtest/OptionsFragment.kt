package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentOptionsBinding
import com.google.firebase.auth.FirebaseAuth

class OptionsFragment : Fragment() {
    private lateinit var binding: FragmentOptionsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        // Set up click listeners using View Binding
        binding.buttonProfile.setOnClickListener {
            if (auth.currentUser == null) {
                showSignInDialog()
            } else {
                Toast.makeText(context, "Profile clicked", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.action_homePageFragment_to_profileFragment)
            }
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

        binding.buttonFoodSpots.setOnClickListener {
            Toast.makeText(context, "Map clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_mapFragment)
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

        binding.buttonScheduleMeal.setOnClickListener {
            Toast.makeText(context, "Schedule Meal clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_homePageFragment_to_scheduleMealsFragment)
        }

        return binding.root
    }

    private fun showSignInDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Restricted Access")
            setMessage("Sign in or sign up to create an account and access your profile")
            setPositiveButton("Sign In") { dialog, which ->
                findNavController().navigate(R.id.signInFragment)
            }
            setNegativeButton("Close") { dialog, which ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

}
