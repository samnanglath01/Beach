package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.beachtest.databinding.FragmentEditDietaryPreferenceBinding

class EditDietaryPreferenceFragment : Fragment() {
    private lateinit var binding: FragmentEditDietaryPreferenceBinding
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDietaryPreferenceBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        loadExistingPreferences()

        // Set up click listener for the forgot guest button to navigate to the guest home screen
        binding.backToProfileButton.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_editDietaryPreferenceFragment_to_profileFragment)
        }
        binding.saveEditDietbutton.setOnClickListener {
            savePreferences()

        }

        return binding.root
    }

    private fun loadExistingPreferences() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            firestore.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        document.getString("dietaryPreference")?.let {
                            updateUI(it)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        "Failed to load preferences: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun updateUI(dietaryPreference: String) {
        val radioButtonId = when (dietaryPreference) {
            "Classic" -> R.id.radio_classic
            "Pescatarian" -> R.id.radio_pescatarian
            "Halal" -> R.id.radio_halal
            "Vegetarian" -> R.id.radio_vegetarian
            "Vegan" -> R.id.radio_vegan
            else -> -1
        }
        if (radioButtonId != -1) {
            binding.radioGroup.check(radioButtonId)
        }
    }



    private fun savePreferences() {
        val selectedOptionId = binding.radioGroup.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedRadioButton = binding.radioGroup.findViewById<RadioButton>(selectedOptionId)
            val dietaryPreference = selectedRadioButton.text.toString()
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (userId != null) {
                val userPreferences = hashMapOf("dietaryPreference" to dietaryPreference)
                firestore.collection("Users").document(userId)
                    .set(userPreferences, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Dietary preference updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        view?.findNavController()
                            ?.navigate(R.id.action_editDietaryPreferenceFragment_to_profileFragment)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            context,
                            "Error updating preference: ${e.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            } else {
                Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Please select a dietary preference", Toast.LENGTH_SHORT).show()
        }

    }


}