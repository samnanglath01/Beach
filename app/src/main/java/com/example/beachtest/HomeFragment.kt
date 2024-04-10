package com.example.beachtest

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.DayOfWeek
import java.time.LocalDate

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDiningHallButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDiningHallButtons() {
        val today = LocalDate.now().dayOfWeek

        binding.parksidehomebutton.setOnClickListener {
            if (today == DayOfWeek.SATURDAY) {
                Toast.makeText(context, "Parkside is closed today. Please select another dining hall.", Toast.LENGTH_LONG).show()
            } else {
                saveDiningHallChoice("Parkside")
                Toast.makeText(context, "Parkside selected", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_homePageFragment_to_mealTimeFragment)
            }
        }

        binding.hillsidehomebutton.setOnClickListener {
            if (today == DayOfWeek.SUNDAY) {
                Toast.makeText(context, "Hillside is closed today. Please select another dining hall.", Toast.LENGTH_LONG).show()
            } else {
                saveDiningHallChoice("Hillside")
                Toast.makeText(context, "Hillside selected", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_homePageFragment_to_mealTimeFragment)
            }
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