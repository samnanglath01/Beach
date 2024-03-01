package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentDietaryPreferenceBinding
import com.example.beachtest.databinding.FragmentForgotPasswordEmailBinding


class ForgotPasswordEmailFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordEmailBinding.inflate(inflater, container, false)

        binding.verificationButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_forgotPasswordEmailFragment_to_signInFragment)
        }

        binding.backToLogInButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_forgotPasswordEmailFragment_to_signInFragment)
        }

        return binding.root
    }

}