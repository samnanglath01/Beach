package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentAllergyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
//samnang lath
class AllergyFragment : Fragment() {
    private lateinit var binding: FragmentAllergyBinding // Assuming  using View Binding

    // Initialize an instance of FirebaseFirestore to interact with your Firestore database.
    private val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllergyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Retrieve the dietaryPreference argument
        val dietaryPreference = arguments?.getString("dietaryPreference")
// Set up an OnClickListener for the 'saveAllergy' button defined in your layout.
        // When the button is clicked, the saveAllergies() funct ion will be called
        binding.saveAllergy.setOnClickListener { // Assuming  have a 'Save' button with this ID
            // This function should contain the logic to save allergy information to Firestore.

            saveAllergies(dietaryPreference)
            findNavController().navigate(R.id.action_allergyFragment_to_homeFragment)
        }
    }
    //Samnang Lath
    private fun saveAllergies(dietaryPreference: String?) {
        // Initialize a mutable list to hold the selected allergies.
        // Initialize a mutable list to hold the selected allergies.
        val checkedAllergies = hashMapOf<String, Any>()

// Add only the checked allergies to the map
        if (binding.checkBoxMilk.isChecked) checkedAllergies["containsMilk"] = true
        if (binding.checkBoxFish.isChecked) checkedAllergies["containsFish"] = true
        if (binding.checkBoxSoy.isChecked) checkedAllergies["containsSoy"] = true
        if (binding.checkBoxPeanuts.isChecked) checkedAllergies["containsPeanuts"] = true
        if (binding.checkBoxTreenuts.isChecked) checkedAllergies["containsTreeNuts"] = true
        if (binding.checkBoxEggs.isChecked) checkedAllergies["containsEggs"] = true
        if (binding.checkBoxShellfish.isChecked) checkedAllergies["containsShellfish"] = true
        if (binding.checkBoxSesame.isChecked) checkedAllergies["containsSesame"] = true
        if (binding.checkBoxWheat.isChecked) checkedAllergies["containsWheat"] = true

// Retrieve the current user's unique ID from Firebase Authentication
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { id ->
            // Create a map to hold the user's dietary preference and their allergies
            val userData = hashMapOf<String, Any>()
            dietaryPreference?.let {
                userData["dietaryPreference"] = it
            }
            userData.putAll(checkedAllergies)

            // Save the user data to Firestore, merging it with existing data
            db.collection("Users").document(id).set(
                userData,
                SetOptions.merge()
            ).addOnSuccessListener {
                Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Failed to save data: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}