package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentAllergyBinding
import com.example.beachtest.databinding.FragmentDietaryPreferenceBinding

class AllergyFragment : Fragment() {

    private lateinit var binding: FragmentAllergyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllergyBinding.inflate(inflater, container, false)

        binding.saveAllergy.setOnClickListener {
            it.findNavController().navigate(R.id.action_allergyFragment_to_homePageFragment)
        }

        binding.backToDietary.setOnClickListener {
            it.findNavController().navigate(R.id.action_allergyFragment_to_dietaryPreferenceFragment)
        }

        return binding.root
    }

}