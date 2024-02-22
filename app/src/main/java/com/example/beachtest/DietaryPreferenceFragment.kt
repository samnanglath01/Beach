package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentDietaryPreferenceBinding
import com.example.beachtest.databinding.FragmentForgotPasswordEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
//Samnang Lath
class DietaryPreferenceFragment : Fragment() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: FragmentDietaryPreferenceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentDietaryPreferenceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveDietbutton.setOnClickListener {
            val selectedOptionId = binding.radioGroup.checkedRadioButtonId
            // Check if a radio button was selected
            if (selectedOptionId != -1) {
                val selectedRadioButton = binding.radioGroup.findViewById<RadioButton>(selectedOptionId)
                val dietaryPreference = selectedRadioButton.text.toString()

                // Call saveDietaryPreference and navigate to AllergyFragment upon success
                saveDietaryPreference(dietaryPreference) {
                    findNavController().navigate(R.id.action_dietaryPreferenceFragment_to_allergyFragment)
                }
            } else {
                // Inform the user if no dietary preference is selected
                Toast.makeText(context, "Please select a dietary preference", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveDietaryPreference(dietaryPreference: String, onSuccess: () -> Unit) {
        // Retrieve the current user's ID from Firebase Authentication.
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { id ->
            // Check if the userId is not null.
            val userPreferences = hashMapOf("dietaryPreference" to dietaryPreference)
// Access the 'Users' collection in Firestore, and get the document corresponding to the userId.
            firestore.collection("Users").document(id)
                // Set the userPreferences map in the document. Use SetOptions.merge() to merge this data
                // with any existing document data (this prevents overwriting other fields in the document).
                .set(userPreferences, SetOptions.merge())
                .addOnSuccessListener {
                    Toast.makeText(context, "Preferences saved successfully", Toast.LENGTH_SHORT).show()
                    onSuccess() // This line is crucial; it triggers the navigation
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error saving preferences: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
