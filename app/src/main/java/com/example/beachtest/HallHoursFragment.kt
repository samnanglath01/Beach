package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentHallHoursBinding

class HallHoursFragment : Fragment() {
    private lateinit var binding: FragmentHallHoursBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHallHoursBinding.inflate(inflater, container, false)

        // Set up click listener for the back home button
        binding.backHomeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_hallHoursFragment_to_homePageFragment)
        }

        return binding.root
    }
}