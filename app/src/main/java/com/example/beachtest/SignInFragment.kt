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
//Samnang Lath & Marlen Dizon
//code for sign in
// using viewbinding

class SignInFragment : Fragment() {
    // Declare a variable for View Binding to access views in the fragment layout
    private lateinit var binding: FragmentSignInBinding
    // Declare a variable for Firebase Authentication to handle user authentication.
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        //  FirebaseAuth instance to work with Firebase Authentication.
        auth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            signInUser()
        }
        //bind with singupButton
        // Set an OnClickListener on the 'loginButton' defined in the layout.
        // When the button is clicked, the 'signInUser' function is called to handle user sign-in.
        binding.loginButton.setOnClickListener {
            signInUser()
        }

        // Set an onClickListener on the 'signUpButton'. When clicked, it navigates to the SignUpFragment.
        // This allows users to move to the sign-up screen if they don't have an account.
        binding.signUpButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

// Set an OnClickListener on the 'forgotPasswordButton'. When clicked, it navigates to the ForgotPasswordEmailFragment.
        // This provides users a way to reset their password if they've forgotten it.

        binding.forgotPasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordEmailFragment)
        }

        // Set up click listener for the forgot guest button to navigate to the guest home screen
        binding.guestButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_homePageFragment)
        }

        return binding.root
    }
    //Samnang Lath
    private fun signInUser() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-in success, navigate to homeFragment
                    findNavController().navigate(R.id.action_signInFragment_to_homePageFragment)
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