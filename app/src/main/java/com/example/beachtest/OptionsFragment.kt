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
            it.findNavController().navigate(R.id.action_optionsFragment_to_profileFragment)
        }

        binding.buttonPhotographyTips.setOnClickListener {
            Toast.makeText(context, "Photography Tips clicked", Toast.LENGTH_SHORT).show();
            //it.findNavController().navigate(R.id.action_optionsFragment_to_profileFragment)
            it.findNavController().navigate(R.id.action_optionsFragment_to_photographyTipFragment)
        }

        binding.buttonPrices.setOnClickListener {
            Toast.makeText(context, "Prices clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_optionsFragment_to_pricesFragment)
        }

        binding.buttonHours.setOnClickListener {
            Toast.makeText(context, "Hours clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_optionsFragment_to_hallHoursFragment)
        }

        binding.buttonVirtualTour.setOnClickListener{
            Toast.makeText(context, "Virtual Tour clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_optionsFragment_to_virtualTourFragment)
        }


        return binding.root
    }
}
