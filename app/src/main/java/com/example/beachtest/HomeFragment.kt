package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    // Possibly refresh HomeFragment data if needed, but do not replace it.
                    true
                }
                R.id.bottom_options -> {
                    findNavController().navigate(R.id.action_homeFragment_to_optionsFragment)
                    true
                }
                else -> false
            }
        }

        setupDiningHallButtons()
    }

    private fun setupDiningHallButtons() {
        binding.parksidehomebutton.setOnClickListener {
            saveDiningHallChoice("Parkside")
            findNavController().navigate(R.id.action_homeFragment_to_mealTimeFragment)
        }

        binding.hillsidehomebutton.setOnClickListener {
            saveDiningHallChoice("Hillside")
            findNavController().navigate(R.id.action_homeFragment_to_mealTimeFragment)
        }

        binding.beachsidehomebutton.setOnClickListener {
            saveDiningHallChoice("Beachside")
            findNavController().navigate(R.id.action_homeFragment_to_mealTimeFragment)
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
