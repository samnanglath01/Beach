package com.example.beachtest.Users.ui

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
//Samnang Lath
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Retrieve the user's preference
        val preferences = getSharedPreferences("YourAppPreferences", Context.MODE_PRIVATE)
        val shouldShowNotifications = preferences.getBoolean("ReceiveNotifications", true)

        if (shouldShowNotifications) {
            // Proceed to show the notification
            sendNotification(remoteMessage)
        }
        // If shouldShowNotifications is false, simply don't show the notification
    }

    private fun sendNotification(message: RemoteMessage) {
        // Your logic to build and show the notification
    }
}