package com.example.beachtest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseHelper = DatabaseHelper.DatabaseHelper(this)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        if (!prefs.getBoolean("data_populated", false)) {
            // Calls the populateInitialData method of the databaseHelper object.
            databaseHelper.populateInitialData()

            // After populating the database with initial data, updates the SharedPreferences to set
            // "data_populated" to true.
            prefs.edit().putBoolean("data_populated", true).apply()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.navHostfragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}