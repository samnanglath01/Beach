package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentFoodPantryBinding

class FoodPantryFragment : Fragment() {
    private lateinit var binding: FragmentFoodPantryBinding
    // Luis Flores and Samnang Lath
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFoodPantryBinding.inflate(inflater, container, false)

        binding.backHomeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_foodPantryFragment_to_homePageFragment)
        }

        return binding.root
    }
}