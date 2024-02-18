package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            signInUser()
        }

        binding.signUpButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }



        binding.forgotPasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordEmailFragment)
        }

        return binding.root
    }
    private fun signInUser() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-in success, navigate to homeFragment
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(context, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
        }
    }
}