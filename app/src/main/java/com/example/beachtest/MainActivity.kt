package com.example.beachtest


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
//samnang lath
class MainActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    //Samnang Lath
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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