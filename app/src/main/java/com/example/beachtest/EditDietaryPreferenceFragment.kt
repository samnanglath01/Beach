package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentEditDietaryPreferenceBinding
import com.example.beachtest.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class EditDietaryPreferenceFragment : Fragment() {
    private lateinit var binding: FragmentEditDietaryPreferenceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDietaryPreferenceBinding.inflate(inflater, container, false)

        // Set up click listener for the forgot guest button to navigate to the guest home screen
        binding.backToProfileButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_editDietaryPreferenceFragment_to_profileFragment)
        }

        return binding.root
    }

}