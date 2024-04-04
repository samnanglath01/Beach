package com.example.beachtest.Users.ui

import android.util.Log
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Usercustomizepreferences {

    private val db = FirebaseFirestore.getInstance()

    fun fetchUserPreferencesAndQueryMenu(onResult: (List<DocumentSnapshot>) -> Unit, onError: (Exception) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let { uid ->
            db.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userAllergiesMap = document.data
                            ?.filter { it.key.startsWith("contains") && it.value == true }
                            ?.map { it.key to it.value as Boolean }
                            ?.toMap()
                            ?: mapOf()

                        if (userAllergiesMap.isNotEmpty()) {
                            queryMenu(userAllergiesMap, onResult, onError)
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

    private fun queryMenu(
        allergiesMap: Map<String, Boolean>,
        onResult: (List<DocumentSnapshot>) -> Unit,
        onError: (Exception) -> Unit
    ) {
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