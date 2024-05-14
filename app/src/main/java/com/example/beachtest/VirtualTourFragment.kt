package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentVirtualTourBinding

// Luis Flores and Sebastian Tadeo
class VirtualTourFragment : Fragment() {
    private lateinit var binding: FragmentVirtualTourBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVirtualTourBinding.inflate(inflater, container, false)

        binding.backHomeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_virtualTourFragment_to_homePageFragment)
        }

        // Set up click listeners to navigate to each dining hall
        // Parkside tour option
        binding.parksidehomebutton.setOnClickListener {
            Toast.makeText(context, "Parkside clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_virtualTourFragment_to_parksideTourFragment)
        }

        // Hillside tour option
        binding.hillsidehomebutton.setOnClickListener {
            Toast.makeText(context, "Hillside clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_virtualTourFragment_to_hillsideTourFragment)
        }

        // Beachside tour option
        binding.beachsidehomebutton.setOnClickListener {
            Toast.makeText(context, "Beachside clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_virtualTourFragment_to_beachsideTourFragment)

        }

        return binding.root

    }
}