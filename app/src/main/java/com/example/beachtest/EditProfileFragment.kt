package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.beachtest.Users.ui.UserManager
import com.example.beachtest.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
//Samnang Lath
    private lateinit var binding: FragmentEditProfileBinding
    private val userManager = UserManager()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        // Set up click listener for the forgot guest button to navigate to the guest home screen


        return binding.root
    }
    //Samnang Lath
    // This method is called immediately after onCreateView() and is used to finalize the creation of the fragment's view.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up an onClickListener for the 'backToProfileButton'. When clicked, it navigates the user back to the profile fragment.

        binding.backToProfileButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

        // Set up an onClickListener for the 'SaveeditButton'. This initiates the process to save the updated user profile information.

        binding.SaveeditButton.setOnClickListener {
            // Retrieve the text input from the email and username EditText fields and trim any leading or trailing whitespace.

            val newEmail = binding.emailEditText.text.toString().trim()
            val newUsername = binding.usernameText.text.toString().trim()
            // Initialize a map to hold the updated user data.
            val updatedData = mutableMapOf<String, Any>()
            // If the new email is not empty, add it to the update map with the key "email".
            if (newEmail.isNotEmpty()) updatedData["email"] = newEmail  // Assuming your Firestore field is named "email"
            // If the new username is not empty, add it to the update map with the key "username".
            if (newUsername.isNotEmpty()) updatedData["username"] = newUsername  // Assuming your Firestore field is named "username"
// Check if there's any data to update.
            if (updatedData.isNotEmpty()) {
                // Call the userManager to update the current user with the new data. Handle success or failure with a Toast message.
                userManager.updateCurrentUser(updatedData) { success ->
                    if (success) {
                        // Display a success message if the profile update is successful.
                        Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
                // If there are no changes, inform the user with a Toast message.
            } else {
                Toast.makeText(context, "No changes to update", Toast.LENGTH_SHORT).show()
            }
        }
    }
}