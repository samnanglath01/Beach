package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.beachtest.databinding.FragmentAllergyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AllergyFragment : Fragment() {
    private lateinit var binding: FragmentAllergyBinding // Assuming you're using View Binding
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

        binding.saveAllergy.setOnClickListener { // Assuming you have a 'Save' button with this ID
            saveAllergies()
        }
    }

    private fun saveAllergies() {
        val selectedAllergies = mutableListOf<String>()
        if (binding.checkBoxMilk.isChecked) selectedAllergies.add("Milk")
        if (binding.checkBoxFish.isChecked) selectedAllergies.add("Fish")
        if (binding.checkBoxSoy.isChecked) selectedAllergies.add("Soy")
        if (binding.checkBoxPeanuts.isChecked) selectedAllergies.add("Peanuts")
        if (binding.checkBoxTreenuts.isChecked) selectedAllergies.add("Tree nuts")
        if (binding.checkBoxEggs.isChecked) selectedAllergies.add("Eggs")
        if (binding.checkBoxShellfish.isChecked) selectedAllergies.add("ShellFish")
        if (binding.checkBoxSesame.isChecked) selectedAllergies.add("Sesame")
        if (binding.checkBoxWheat.isChecked) selectedAllergies.add("Wheat")
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { id ->
            db.collection("users").document(id).set(
                mapOf("allergies" to selectedAllergies),
                SetOptions.merge()
            ).addOnSuccessListener {
                Toast.makeText(context, "Allergies saved successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Failed to save allergies: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}