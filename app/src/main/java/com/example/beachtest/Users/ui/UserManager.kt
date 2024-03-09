package com.example.beachtest.Users.ui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUsername(onResult: (String?) -> Unit) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val username = document.getString("username")
                        onResult(username)
                    } else {
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
    fun getCurrentUserEmail(onResult: (String?) -> Unit) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Assuming the email field in your Firestore is named "email"
                        val email = document.getString("email")
                        onResult(email)
                    } else {
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

}