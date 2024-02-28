package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentHomeBinding
import com.example.beachtest.databinding.FragmentSignUpBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.beachsideButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_weekDayFragment)
        }

        binding.hillsideButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_weekDayFragment)
        }

        binding.parksideButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_weekDayFragment)
        }
        return binding.root
    }
}