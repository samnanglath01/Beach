package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentProfileBinding
import com.example.beachtest.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.editprofileButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.editDietButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editDietaryPreferenceFragment)
        }

        binding.DeleteButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
        }

        return binding.root
    }

}