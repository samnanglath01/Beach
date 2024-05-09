package com.example.beachtest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentMenuItemsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
    private var guestId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuItemsBinding.inflate(inflater, container, false)
        guestId = loadGuestId()
        return binding.root
    }

    private fun loadGuestId(): String? {
        val prefs = activity?.getSharedPreferences("GuestPrefs", Context.MODE_PRIVATE)
        return prefs?.getString("guestId", null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserSelectionsAndDisplayMenu()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserSelectionsAndDisplayMenu() {
        val userUid = auth.currentUser?.uid

        lifecycleScope.launch {
            try {
                val docRef = if (userUid != null) {
                    firestore.collection("Users").document(userUid)
                } else if (guestId != null) {
                    firestore.collection("Guests").document(guestId!!)
                } else {
                    Toast.makeText(context, "No user or guest data available.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // Fetch user's selections, allergies, and dietary preferences from Firestore
                val userDoc = docRef.get().await()
                val diningHall = userDoc.getString("diningHall")
                val mealTime = userDoc.getString("mealTime")

                if (diningHall != null && mealTime != null) {
                    if (userUid != null) {
                        // Fetch additional data for authenticated users
                        val dietaryPreference = userDoc.getString("dietaryPreference")
                        val allergies = userDoc.getData()?.filterKeys { it.startsWith("contains") }
                            ?.filterValues { it == true }?.keys ?: emptySet()
                        displayMenuItems(diningHall, mealTime, dietaryPreference, allergies)
                    } else {
                        // Display for guests without dietary preferences and allergies
                        displayMenuItems(diningHall, mealTime, null, emptySet())
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error retrieving data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayMenuItems(
        diningHall: String,
        mealTime: String,
        dietaryPreference: String?,
        allergies: Set<String>
    ) {
        // Determine the corresponding fields for Firestore based on user selection
        val today = LocalDate.now()

        // Define the date ranges
        val startDate1 = LocalDate.of(2024, 4, 8)
        val endDate1 = LocalDate.of(2024, 4, 14)
        val startDate2 = LocalDate.of(2024, 4, 15)
        val endDate2 = LocalDate.of(2024, 4, 21)
        val startDate3 = LocalDate.of(2024, 4, 22)
        val endDate3 = LocalDate.of(2024, 4, 28)
        val startDate4 = LocalDate.of(2024, 4, 29)
        val endDate4 = LocalDate.of(2024, 5, 5)
        val startDate5 = LocalDate.of(2024, 3, 25)
        val startDate5ext = LocalDate.of(2024, 4, 1)
        val endDate5ext = LocalDate.of(2024, 4, 7)
        val endDate5 = LocalDate.of(2024, 3, 31)
        val startDate6 = LocalDate.of(2024, 5, 6)
        val endDate6 = LocalDate.of(2024, 5, 12)
        val startDate7 = LocalDate.of(2024, 5, 13)
        val endDate7 = LocalDate.of(2024, 5, 19)

        // Check if today is within the date range
        val isWithinCycle1 =
            !today.isBefore(startDate1) && !today.isAfter(endDate1) || !today.isBefore(startDate6) && !today.isAfter(endDate6) || !today.isBefore(startDate7) && !today.isAfter(endDate7)
        val isWithinCycle2 = !today.isBefore(startDate2) && !today.isAfter(endDate2)
        val isWithinCycle3 = !today.isBefore(startDate3) && !today.isAfter(endDate3)
        val isWithinCycle4 = !today.isBefore(startDate4) && !today.isAfter(endDate4)
        val isWithinCycle5 =
            !today.isBefore(startDate5) && !today.isAfter(endDate5) || !today.isBefore(startDate5ext) && !today.isAfter(
                endDate5ext
            )

        val dayOfWeek = today.dayOfWeek.toString().lowercase().capitalize()
        val servedTodayField = "served$dayOfWeek"

        var menuItemsQuery = firestore.collection("menu")
            .whereEqualTo("served${diningHall.capitalize(Locale.ROOT)}", true)
            .whereEqualTo("served${mealTime.capitalize(Locale.ROOT)}", true)
            .whereEqualTo(servedTodayField, true)

        // Apply the 'servedCycle' filters if within the date range
        if (isWithinCycle1) {
            menuItemsQuery = menuItemsQuery.whereEqualTo("servedCycle1", true)
        } else if (isWithinCycle2) {
            menuItemsQuery = menuItemsQuery.whereEqualTo("servedCycle2", true)
        } else if (isWithinCycle3) {
            menuItemsQuery = menuItemsQuery.whereEqualTo("servedCycle3", true)
        } else if (isWithinCycle4) {
            menuItemsQuery = menuItemsQuery.whereEqualTo("servedCycle4", true)
        } else if (isWithinCycle5) {
            menuItemsQuery = menuItemsQuery.whereEqualTo("servedCycle5", true)
        } else {
            Toast.makeText(context, "Out of menu cycle range", Toast.LENGTH_SHORT).show()
        }

        if (dietaryPreference != null) {
            menuItemsQuery = applyDietaryFilters(menuItemsQuery, dietaryPreference)
        }

        // Exclude menu items containing allergens
        if (allergies.isNotEmpty()) {
            allergies.forEach { allergy ->
                menuItemsQuery = menuItemsQuery.whereEqualTo(allergy, false)
            }
        }

        // Filter based on dietary preferences
        /*
        dietaryPreference?.let { preference ->
            when {
                preference.contains("Pescatarian", ignoreCase = true) -> menuItemsQuery =
                    menuItemsQuery.whereEqualTo("isPescatarian", true)

                preference.contains("Halal", ignoreCase = true) -> menuItemsQuery =
                    menuItemsQuery.whereEqualTo("isHalal", true)

                preference.contains("Vegetarian", ignoreCase = true) -> menuItemsQuery =
                    menuItemsQuery.whereEqualTo("isVegetarian", true)

                preference.contains("Vegan", ignoreCase = true) -> menuItemsQuery =
                    menuItemsQuery.whereEqualTo("isVegan", true)
            }
        }*/

        // Execute the query and handle the results
        menuItemsQuery.get().addOnSuccessListener { documents ->
            val menuItemsLayout = binding.menuItemsLayout
            menuItemsLayout.removeAllViews()  // Clear previous buttons if any
            documents.forEach { document ->
                val button = Button(context).apply {
                    // For debugging, use a hardcoded string
                    text = document.id
                    textSize = 16f // Ensure the text size is reasonable
                    setTextColor(ContextCompat.getColor(context, android.R.color.black)) // Set the text color explicitly
                    backgroundTintList = ContextCompat.getColorStateList(context, R.color.yellow) // Set the background tint to yellow
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).also { it.topMargin = 8 }
                    setOnClickListener {
                        navigateToDishDetails(document.id)
                    }
                }
                menuItemsLayout.addView(button)
            }
        }.addOnFailureListener {
            // Handle any errors here
            Toast.makeText(context, "Failed to load menu items", Toast.LENGTH_SHORT).show()
        }
    }
    private fun applyDietaryFilters(query: Query, dietaryPreference: String): Query {
        var modifiedQuery = query
        if (dietaryPreference.contains("Pescatarian", ignoreCase = true)) {
            modifiedQuery = modifiedQuery.whereEqualTo("isPescatarian", true)
        }
        if (dietaryPreference.contains("Halal", ignoreCase = true)) {
            modifiedQuery = modifiedQuery.whereEqualTo("isHalal", true)
        }
        if (dietaryPreference.contains("Vegetarian", ignoreCase = true)) {
            modifiedQuery = modifiedQuery.whereEqualTo("isVegetarian", true)
        }
        if (dietaryPreference.contains("Vegan", ignoreCase = true)) {
            modifiedQuery = modifiedQuery.whereEqualTo("isVegan", true)
        }
        return modifiedQuery
    }

    private fun navigateToDishDetails(dishId: String) {
        val action = MenuItemsFragmentDirections.actionMenuItemsFragmentToDishDetailsFragment(dishId)
        if (isAdded) {
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}