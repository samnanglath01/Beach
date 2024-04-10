package com.example.beachtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {
//Samnang Lath
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Create a simple notification
    // Get the NotificationManager system service to manage notifications.
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
// Generate a random integer to use as a unique notification ID.
        val notificationID = Random.nextInt()// Generates a random integer that will serve as the unique ID for the notification
    // Create an intent that will be triggered when the notification is tapped. This intent will open MainActivity.
        val intent = Intent(this, MainActivity::class.java)
    // Ensure that MainActivity is at the top of the stack and clear any other activities.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    // Define flags for the PendingIntent based on the Android version.
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Define flags for the PendingIntent based on the Android version.

            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        } else {
            // Define flags for the PendingIntent based on the Android version.

            PendingIntent.FLAG_ONE_SHOT
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentFlags)

    // Build the notification, specifying the channel ID, title, text, icon, and the intent to be triggered.
    val notificationBuilder = NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
        .setContentTitle(remoteMessage.notification?.title ?: "Default Title") // Use the title from the FCM message, or a default one.
        .setContentText(remoteMessage.notification?.body ?: "Default Text") // Use the body text from the FCM message, or a default one.
        .setSmallIcon(R.drawable.ic_notification) // Set the small icon for the notification from drawable resources.
        .setContentIntent(pendingIntent) // Set the intent to fire when the notification is tapped.
        .setAutoCancel(true) // Make the notification dismissible when tapped.


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("YOUR_CHANNEL_ID", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
        // Register the channel with the system.
        notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationID, notificationBuilder.build())
    }



}