package com.example.beachtest

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import android.widget.Toast
import com.example.beachtest.databinding.FragmentOptionsBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

class OptionsFragment : Fragment() {
    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater, container, false)

        // Set up click listeners using View Binding
        binding.buttonProfile.setOnClickListener {
            Toast.makeText(context, "Profile clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_profileFragment)
        }
        binding.buttonSetPlan.setOnClickListener {
            Toast.makeText(context, "Meal plan clicked clicked", Toast.LENGTH_SHORT).show();
            //it.findNavController().navigate(R.id.action_optionsFragment_to_profileFragment)
            it.findNavController().navigate(R.id.action_homePageFragment_to_setMealPlan2)
        }

        binding.buttonPhotographyTips.setOnClickListener {
            Toast.makeText(context, "Photography Tips clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_photographyTipFragment)
        }

        binding.buttonPrices.setOnClickListener {
            Toast.makeText(context, "Prices clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_pricesFragment)
        }

        binding.buttonHours.setOnClickListener {
            Toast.makeText(context, "Hours clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_hallHoursFragment)
        }

        binding.buttonVirtualTour.setOnClickListener {
            Toast.makeText(context, "Virtual Tour clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_virtualTourFragment)
        }

        binding.buttonPantry.setOnClickListener {
            Toast.makeText(context, "Food Pantry clicked", Toast.LENGTH_SHORT).show();
            it.findNavController().navigate(R.id.action_homePageFragment_to_foodPantryFragment)
        }

        binding.buttonVendors.setOnClickListener {
            Toast.makeText(context, "Campus Vendors clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_homePageFragment_to_foodTrucksFragment)
        }
        //Samnang Lath
        // Setup listener for the meal update switch and return the root view of the binding
        setupSwitchListener()
        return binding.root
    }
    // This function sets up a listener on the switch for enabling/disabling meal update notifications
    private fun setupSwitchListener() {
        binding.switchMealUpdates.setOnCheckedChangeListener { _, isChecked ->
            updateUserNotificationPreference(isChecked)
            if (isChecked) {
                setupNotifications(true)
                AlertDialog.Builder(requireContext())
                    .setTitle("Notification")
                    .setMessage("You have successfully turn on your meal updated notification.")
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        dialog.dismiss()
                    }

                    .show()// Show the alert dialog
            } else {
                // If the switch is unchecked (turned off), show a confirmation dialog
                AlertDialog.Builder(requireContext())
                    .setTitle("Notification")
                    .setMessage("You have successfully turn off your meal updated notification.")
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
    // Update the shared preferences to reflect the user's choice about receiving notifications
    private fun updateUserNotificationPreference(shouldReceiveNotifications: Boolean) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean("ReceiveNotifications", shouldReceiveNotifications)
            apply()
        }
    }

    private fun setupNotifications(shouldReceiveNotifications: Boolean) {
        createNotificationChannel()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                val msg = "FCM Token: $token"
                Log.d("FCM", msg)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                sendRegistrationToServer(token, shouldReceiveNotifications)
            } else {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                Toast.makeText(
                    context,
                    "Fetching FCM registration token failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Meal Reminders" // Example: R.string.channel_name -> "Meal Reminders"
            val descriptionText =
                "Notifications for meal reminders" // Example: R.string.channel_description -> "Notifications for meal reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("meal_reminders", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendRegistrationToServer(token: String?, shouldReceiveNotifications: Boolean) {
        Log.d("FCM", "Send token to server: $token")
        token?.let {
            val firestore = FirebaseFirestore.getInstance()
            val tokenDocument = hashMapOf(
                "token" to it,
                "receiveNotifications" to shouldReceiveNotifications, // Add this line
                "createdAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("tokens").document(it).set(tokenDocument)
                .addOnSuccessListener { documentReference ->
                    Log.d("FCM", "Preferences successfully stored in Firestore.")
                }
                .addOnFailureListener { e ->
                    Log.e("FCM", "Error storing preferences in Firestore", e)
                }
        }
    }
}