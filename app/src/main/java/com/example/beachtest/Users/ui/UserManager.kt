package com.example.beachtest.Users.ui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
//Samnang Lath
// Function to retrieve the current user's username from Firestore
    fun getCurrentUsername(onResult: (String?) -> Unit) {
    // Get the currently logged-in user

    val currentUser = auth.currentUser
    // Check if a user is logged in
        if (currentUser != null) {
            val userId = currentUser.uid
            // Access the user's document in the "Users" collection using their userId
            db.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    // Check if the document exists
                    if (document != null && document.exists()) {
                        val username = document.getString("username")
                        // Return the username through the callback
                        onResult(username)
                    } else {
                        // Return null if the document doesn't exist
                        onResult(null)
                    }
                }
                .addOnFailureListener {
                    onResult(null)
                }
        } else {
            onResult(null)
        }
    }
    // Function to retrieve the current user's email from Firestore
    fun getCurrentUserEmail(onResult: (String?) -> Unit) {
        // Get the currently logged-in user
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            // Access the user's document in the "Users" collection using their userId
            db.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    // Access the user's document in the "Users" collection using their userId
                    if (document != null && document.exists()) {// Check if the document exists
                        // Assuming the email field in your Firestore is named "email"
                        val email = document.getString("email")
                        onResult(email)
                    } else {
                        onResult(null)
                    }
                }
                // Return null in case of any failure during the Firestore operation

                .addOnFailureListener {
                    onResult(null)
                }
        } else {
            onResult(null)
        }
    }

    // Function to update the current user's data in Firestore
    fun updateCurrentUser(updatedData: Map<String, Any>, onComplete: (Boolean) -> Unit) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid // Get the user's unique ID
            // Update the user's document in the "Users" collection with the provided data
            db.collection("Users").document(userId)
                .update(updatedData)
                .addOnSuccessListener {
                    onComplete(true)// Invoke the callback with true to indicate success
                }
                .addOnFailureListener {
                    onComplete(false)
                }
        } else {
            onComplete(false)
        }
    }
    //SAmnang Lath
    fun updateUsername(newUsername: String, onResult: (Boolean) -> Unit) {
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val userId = user.uid
            val userDocRef = db.collection("Users").document(userId)

            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Check if the new username is different from the current one
                        val currentUsername = document.getString("username")
                        if (currentUsername != newUsername) {
                            // Update the username in Firestore
                            userDocRef.update("username", newUsername)
                                .addOnSuccessListener {
                                    onResult(true) // Username updated successfully
                                }
                                .addOnFailureListener {
                                    onResult(false) // Failed to update username
                                }
                            //SAmnang lath
                        } else {
                            onResult(false) // New username is the same as the current one, no need to update
                        }
                    } else {
                        onResult(false) // Document does not exist
                    }
                }
                .addOnFailureListener {
                    onResult(false) // Failed to fetch the document
                }
        } ?: onResult(false) // User is not logged in
    }
}

