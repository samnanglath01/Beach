package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentResetPasswordBinding
import com.example.beachtest.databinding.FragmentSignInBinding

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)

        binding.savepasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
        }

        binding.backToLogInButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
        }

        return binding.root
    }

}