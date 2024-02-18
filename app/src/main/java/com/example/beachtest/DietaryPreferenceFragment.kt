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
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class DietaryPreferenceFragment : Fragment() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dietary_preference, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<Button>(R.id.saveDietbutton)
        val radioGroup =
            view.findViewById<RadioGroup>(R.id.radioGroup)

        saveButton.setOnClickListener {
            val selectedOptionId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = view.findViewById<RadioButton>(selectedOptionId)
            val dietaryPreference = selectedRadioButton.text.toString()

            saveDietaryPreference(dietaryPreference) {
                findNavController().navigate(R.id.action_dietaryPreferenceFragment_to_allergyFragment)
            }


        }
    }

    private fun saveDietaryPreference(dietaryPreference: String, onSuccess: () -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { id ->
            val userPreferences = hashMapOf(
                "dietaryPreference" to dietaryPreference
                // Add more fields like allergies here
            )

            firestore.collection("Users").document(id)
                .set(
                    userPreferences,
                    SetOptions.merge()
                ) // Use merge to update the document or create it if it doesn't exist
                .addOnSuccessListener {
                    Toast.makeText(context, "Preferences saved successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Error saving preferences: ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
