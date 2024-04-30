package com.example.beachtest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    //Samnang Lath
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Get new FCM registration token
                val token = task.result
                // Log and toast
                val msg = "FCM Token: $token"
                Log.d("FCM", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

                // Optionally, send the token to your server if you have one set up for managing push notifications
                sendRegistrationToServer(token)
            } else {
                // If fetching the token failed, log the exception
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                Toast.makeText(baseContext, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show()
            }
        }

    }
    //Samnang lath
    fun sendRegistrationToServer(token: String?) {
        // Implement this method to send token to your app server.
        Log.d("FCM", "Send token to server: $token")
        // Use Kotlin's safe call operator to only proceed if the token is not null.
        token?.let {
            // Get an instance of Firestore to store the token.
            val firestore = FirebaseFirestore.getInstance()
            // Create a map to hold the token and its creation timestamp.
            val tokenDocument = hashMapOf(
                "token" to token,
                "createdAt" to FieldValue.serverTimestamp() // To store the time the token was saved
            )
// Add the tokenDocument map to the "tokens" collection in Firestore.
            firestore.collection("tokens").add(tokenDocument)
                .addOnSuccessListener {
                    Log.d("FCM", "Token successfully stored in Firestore with document ID: ${it.id}")
                }
                .addOnFailureListener { e ->
                    Log.e("FCM", "Error storing token in Firestore", e)
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.navHostfragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Log out the user when the app is being destroyed
        auth.signOut()
        Log.d("Auth", "User logged out successfully")
    }

}