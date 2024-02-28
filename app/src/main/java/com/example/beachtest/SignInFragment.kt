package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }

        binding.signUpButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.forgotPasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordEmailFragment)
        }

        return binding.root
    }

}