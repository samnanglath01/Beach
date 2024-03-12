package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentEditDietaryPreferenceBinding
import com.example.beachtest.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        // Set up click listener for the forgot guest button to navigate to the guest home screen
        binding.backToProfileButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_editDietaryPreferenceFragment_to_profileFragment)
        }

        return binding.root
    }

}