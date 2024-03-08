package com.example.beachtest

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentSignInBinding
import com.example.beachtest.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Samnang Lath & Marlen Dizon
//singup with with view binding
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        binding.backToLogInButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.signUpButton.setOnClickListener {

            signUpUser()
        }


        return binding.root

    }
    //Samnang Lath
    private fun signUpUser() {
        // Retrieve text inputs from the email and password fields and trim any leading or trailing spaces.
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmpasswordEditText.text.toString().trim()
        val userName= binding.usernameText.text.toString().trim()
        db = FirebaseFirestore.getInstance()
        // Call a separate function to validate the input fields.
        // This typically checks for non-empty inputs, valid email format, matching passwords, etc.
        if (validateInputs(email, password, confirmPassword,userName)) {
            // Use FirebaseAuth to attempt creating a new user with the email and password.
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    // If Firebase successfully creates the user account, log the success.
                    if (task.isSuccessful) {
                        // Sign up success, log the success and navigate
                        Log.d("SignUpSuccess", "createUserWithEmail:success")
                        val user = auth.currentUser
                        val userId = user?.uid

                        // Prepare user data to be saved
                        val userMap = hashMapOf(
                            "username" to userName,
                            "email" to email
                            // Add other user details as needed
                        )

                        // Save the additional user information in Firestore
                        userId?.let {
                            db.collection("Users").document(it)
                                .set(userMap)
                                .addOnSuccessListener {
                                    Log.d("FirestoreSuccess", "User profile created for $userId")
                                    // Navigate to next fragment or activity after successful signup and profile creation
                                    activity?.runOnUiThread {
                                        findNavController().navigate(R.id.action_signUpFragment_to_dietaryPreferenceFragment)
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.w("FirestoreError", "Error creating user profile", e)
                                }
                        }
                    } else {
                        // If sign up fails, log the error and display a message to the user.
                        Log.w("SignUpFailure", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    //Samnang Lath
    private fun validateInputs(email: String, password: String, confirmPassword: String,userName:String): Boolean {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "Fields cannot be empty.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) { // Example: Check for a minimum password length
            Toast.makeText(context, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true // Placeholder
    }
}