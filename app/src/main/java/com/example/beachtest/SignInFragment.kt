package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentSignInBinding

/**
 * SignInFragment allows the user to sign in to their account.
 * This fragment includes options to navigate to sign up, home page, or reset password if the user forgot it.
 * UI and Navigations Written by: Marlen Dizon
 */
class SignInFragment : Fragment() {
    // Binding property to access the views in the layout fragment_sign_in.xml
    private lateinit var binding: FragmentSignInBinding

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null.
     * This will be called between onCreate and onActivityCreated.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        // Set up click listener for the login button to navigate to the HomePageFragment
        binding.loginButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_homePageFragment)
        }

        // Set up click listener for the sign up button to navigate to the SignUpFragment
        binding.signUpButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // Set up click listener for the forgot password button to navigate to the ForgotPasswordEmailFragment
        binding.forgotPasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordEmailFragment)
        }

        // Set up click listener for the forgot guest button to navigate to the guest home screen
        binding.guestButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_homePageFragment)
        }

        // Return the root view of the binding class, which holds all the layout's views
        return binding.root
    }

}