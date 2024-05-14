package com.example.beachtest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.time.DayOfWeek
import java.time.LocalDate
import androidx.navigation.NavDirections
import java.util.UUID


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var guestId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        guestId = loadGuestId() // Load the guest ID from SharedPreferences
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDiningHallButtons()
    }

    private fun loadGuestId(): String? {
        val prefs = activity?.getSharedPreferences("GuestPrefs", Context.MODE_PRIVATE)
        return prefs?.getString("guestId", null)
    }

    private fun saveGuestId(id: String) {
        val prefs = activity?.getSharedPreferences("GuestPrefs", Context.MODE_PRIVATE)
        prefs?.edit()?.putString("guestId", id)?.apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDiningHallButtons() {
        val today = LocalDate.now().dayOfWeek

        binding.parksidehomebutton.setOnClickListener {
            handleDiningHallSelection("Parkside", today == DayOfWeek.SATURDAY)
        }

        binding.hillsidehomebutton.setOnClickListener {
            handleDiningHallSelection("Hillside", today == DayOfWeek.SUNDAY)
        }

        binding.beachsidehomebutton.setOnClickListener {
            handleDiningHallSelection("Beachside", false)
        }
    }

    private fun handleDiningHallSelection(diningHall: String, isClosed: Boolean) {
        if (isClosed) {
            Toast.makeText(context, "$diningHall is closed today. Please select another dining hall.", Toast.LENGTH_LONG).show()
        } else {
            saveDiningHallChoice(diningHall)
            Toast.makeText(context, "$diningHall selected", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_homePageFragment_to_mealTimeFragment)
        }
    }

    private fun saveDiningHallChoice(diningHall: String) {
        val userUid = auth.currentUser?.uid

        if (userUid != null) {
            // Save for logged in user
            firestore.collection("Users").document(userUid)
                .update("diningHall", diningHall)
            // Luis Flores, guest mode
        } else {
            if (guestId == null) {
                guestId = UUID.randomUUID().toString()
                saveGuestId(guestId!!)
            }
            // Save for guest
            firestore.collection("Guests").document(guestId!!)
                .set(mapOf("diningHall" to diningHall), SetOptions.merge())
        }
    }
}
