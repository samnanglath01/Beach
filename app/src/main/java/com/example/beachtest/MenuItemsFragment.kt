package com.example.beachtest

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.beachtest.databinding.FragmentMenuItemsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.Locale

// Luis Flores
class MenuItemsFragment : Fragment() {
    private var _binding: FragmentMenuItemsBinding? = null
    private val binding get() = _binding!!
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMenuItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserSelectionsAndDisplayMenu()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserSelectionsAndDisplayMenu() {
        val userUid = auth.currentUser?.uid ?: return // Get current user UID

        lifecycleScope.launch {
            try {
                // Fetch user's selections, allergies, and dietary preferences from Firestore
                val userDoc = firestore.collection("Users").document(userUid).get().await()
                val diningHall = userDoc.getString("diningHall")
                val mealTime = userDoc.getString("mealTime")
                val dietaryPreference = userDoc.getString("dietaryPreference")
                val allergies = userDoc.getData()?.filterKeys { it.startsWith("contains") }?.filterValues { it == true }

                // Continue only if the dining hall and meal time have been set by the user
                if (diningHall != null && mealTime != null) {
                    displayMenuItems(diningHall, mealTime, dietaryPreference, allergies?.keys ?: emptySet())
                } else {
                    // Handle the case where dining hall or meal time is not set
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayMenuItems(diningHall: String, mealTime: String, dietaryPreference: String?, allergies: Set<String>) {
        // Determine the corresponding fields for Firestore based on user selection
        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek.toString().lowercase().capitalize()
        val servedTodayField = "served$dayOfWeek"

        var menuItemsQuery = firestore.collection("menu")
            .whereEqualTo("served${diningHall.capitalize(Locale.ROOT)}", true)
            .whereEqualTo("served${mealTime.capitalize(Locale.ROOT)}", true)
            .whereEqualTo(servedTodayField, true)

        // Exclude menu items containing allergens
        allergies.forEach { allergy ->
            menuItemsQuery = menuItemsQuery.whereEqualTo(allergy, false)
        }

        // Filter based on dietary preferences
        dietaryPreference?.let { preference ->
            when {
                preference.contains("Pescatarian", ignoreCase = true) -> menuItemsQuery = menuItemsQuery.whereEqualTo("isPescatarian", true)
                preference.contains("Halal", ignoreCase = true) -> menuItemsQuery = menuItemsQuery.whereEqualTo("isHalal", true)
                preference.contains("Vegetarian", ignoreCase = true) -> menuItemsQuery = menuItemsQuery.whereEqualTo("isVegetarian", true)
                preference.contains("Vegan", ignoreCase = true) -> menuItemsQuery = menuItemsQuery.whereEqualTo("isVegan", true)
            }
        }

        // Execute the query and handle the results
        menuItemsQuery.get().addOnSuccessListener { documents ->
            val menuItemsBuilder = StringBuilder()
            for (document in documents) {
                menuItemsBuilder.append(document.id).append("\n")
            }
            // Display the menu items in the TextView
            binding.textViewMenuItemsTitle.text = menuItemsBuilder.toString()
        }.addOnFailureListener {
            // Handle any errors here
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}