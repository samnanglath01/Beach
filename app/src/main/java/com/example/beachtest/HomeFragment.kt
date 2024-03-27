package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Luis Flores
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDiningHallButtons()
    }

    private fun setupDiningHallButtons() {
        binding.parksidehomebutton.setOnClickListener {
            saveDiningHallChoice("Parkside")
            Toast.makeText(context, "Parkside selected", Toast.LENGTH_SHORT).show();
            findNavController().navigate(R.id.action_homePageFragment_to_mealTimeFragment)
        }

        binding.hillsidehomebutton.setOnClickListener {
            saveDiningHallChoice("Hillside")
            Toast.makeText(context, "Hillside selected", Toast.LENGTH_SHORT).show();
            findNavController().navigate(R.id.action_homePageFragment_to_mealTimeFragment)
        }

        binding.beachsidehomebutton.setOnClickListener {
            saveDiningHallChoice("Beachside")
            Toast.makeText(context, "Beachside selected", Toast.LENGTH_SHORT).show();
            findNavController().navigate(R.id.action_homePageFragment_to_mealTimeFragment)
        }
    }

    private fun saveDiningHallChoice(diningHall: String) {
        val userUid = auth.currentUser?.uid ?: return
        firestore.collection("Users").document(userUid)
            .update("diningHall", diningHall)
            .addOnSuccessListener {
                // Handle successful save here if needed
            }
            .addOnFailureListener { e ->
                // Handle failure here if needed
            }
    }
}