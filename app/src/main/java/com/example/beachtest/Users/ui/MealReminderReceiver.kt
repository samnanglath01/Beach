package com.example.beachtest.Users.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.beachtest.R
import kotlin.random.Random
//Samnang Lath
class MealReminderReceiver : BroadcastReceiver() {

    // This method is called when the BroadcastReceiver receives an Intent broadcast.
    override fun onReceive(context: Context?, intent: Intent?) {
        // Retrieve the 'title' string extra from the intent; use "Meal Reminder" as default if it's not found
        val title = intent?.getStringExtra("title") ?: "Meal Reminder"

        // Retrieve the 'message' string extra from the intent; use "Time for your meal!" as default if it's not found
        val message = intent?.getStringExtra("message") ?: "Time for your meal!"

        // Generate a random ID for each notification to ensure uniqueness, avoiding notification overrides
        val notificationId = Random.nextInt()

        // 'context?.let' ensures the following block is executed only if context is not null
        context?.let {
            // Build the notification with necessary details
            val builder = NotificationCompat.Builder(it, "meal_reminders")
                .setSmallIcon(R.drawable.ic_notification) // Set the small icon for the notification, make sure this icon exists in your drawable resources
                .setContentTitle(title) // Set the title of the notification
                .setContentText(message) // Set the text content of the notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the priority of the notification

            // Finally, issue the notification using NotificationManagerCompat with the unique 'notificationId'
            NotificationManagerCompat.from(it).notify(notificationId, builder.build())
        }
    }
}