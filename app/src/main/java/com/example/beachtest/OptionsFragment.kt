package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import android.widget.Toast
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
            //it.findNavController().navigate(R.id.action_optionsFragment_to_profileFragment)
            it.findNavController().navigate(R.id.action_homePageFragment_to_profileFragment)
        }
        binding.buttonSetPlan.setOnClickListener {
            Toast.makeText(context, "Meal plan clicked clicked", Toast.LENGTH_SHORT).show();
            //it.findNavController().navigate(R.id.action_optionsFragment_to_profileFragment)
            it.findNavController().navigate(R.id.action_homePageFragment_to_setMealPlan)
        }

        binding.buttonPhotographyTips.setOnClickListener {
            Toast.makeText(context, "Photography Tips clicked", Toast.LENGTH_SHORT).show();
            //it.findNavController().navigate(R.id.action_optionsFragment_to_profileFragment)
            it.findNavController().navigate(R.id.action_homePageFragment_to_photographyTipFragment)
        }


        return binding.root
    }

    // If using onViewCreated, you could also move the click listener setup here
}
