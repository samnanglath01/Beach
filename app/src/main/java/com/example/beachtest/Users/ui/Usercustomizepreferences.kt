package com.example.beachtest.Users.ui

import android.util.Log
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Usercustomizepreferences {
    // Initialize a Firestore database instance
    private val db = FirebaseFirestore.getInstance()
//Samnang Lath
    fun fetchUserPreferencesAndQueryMenu(onResult: (List<DocumentSnapshot>) -> Unit, onError: (Exception) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let { uid ->

            db.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    // Check if the user document exists in the database
                    if (document.exists()) {
                        // Filter the user's data to find allergies (keys starting with "contains" and value true)
                        val userAllergiesMap = document.data
                            ?.filter { it.key.startsWith("contains") && it.value == true }
                            ?.map { it.key to it.value as Boolean }
                            ?.toMap()
                            ?: mapOf()
// If allergies are found, query the menu with these allergies
                        if (userAllergiesMap.isNotEmpty()) {
                            queryMenu(userAllergiesMap, onResult, onError)
                            // If no allergies are marked, handle accordingly (e.g., show all or no menu items)
                        } else {
                            // Handle the case where there are no allergies marked as true
                            onResult(emptyList()) // You can decide to show all menu items or none
                        }
                    } else {
                        onError(Exception("User document does not exist"))
                    }
                }
                .addOnFailureListener { exception ->
                    onError(exception)
                }
        }
    }
//Samnang Lath
// Private function to query the menu based on the user's allergies
    private fun queryMenu(
        allergiesMap: Map<String, Boolean>,
        onResult: (List<DocumentSnapshot>) -> Unit,
        onError: (Exception) -> Unit
    ) {
    // Start with a base query on the "menu" collection
        var query: Query = db.collection("menu")

        // Add a query condition for each allergen that the user has
        allergiesMap.forEach { (allergen, _) ->
            // We add a condition that the menu item does not contain the allergen
            query = query.whereEqualTo(allergen, false)
        }

        query.get()
            .addOnSuccessListener { querySnapshot ->
                onResult(querySnapshot.documents) // This provides the expected List<DocumentSnapshot>
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}