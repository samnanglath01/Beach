package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentSignUpBinding

/**
 * SignUpFragment provides the user interface for new users to create an account.
 * It includes a form for the user's details and options to either sign up or navigate back to the login screen.
 * UI and Navigations written by: Marlen Dizon
 */
class SignUpFragment : Fragment() {
    // Binding property to access and interact with the layout's views via FragmentSignUpBinding
    private lateinit var binding: FragmentSignUpBinding

    /**
     * onCreateView is called to have the fragment instantiate its user interface view.
     * Here, the layout is inflated using data binding, and event listeners are set up for UI interaction.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null if the fragment does not provide a UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding for easier view management
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        // Navigate back to the SignInFragment when the 'Back to Log In' button is clicked
        binding.backToLogInButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        // Navigate to the DietaryPreferenceFragment after the user clicks the 'Sign Up' button
        // This action assumes the user has filled in their information and is ready to proceed
        binding.signUpButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signUpFragment_to_dietaryPreferenceFragment)
        }

        // Return the root of the inflated layout
        return binding.root
    }
}
