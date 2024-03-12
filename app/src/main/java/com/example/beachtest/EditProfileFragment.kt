package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentEditDietaryPreferenceBinding

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditDietaryPreferenceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDietaryPreferenceBinding.inflate(inflater, container, false)

        // Set up click listener for the back button to navigate to the profile fragment
        binding.backToProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

        return binding.root
    }
}
