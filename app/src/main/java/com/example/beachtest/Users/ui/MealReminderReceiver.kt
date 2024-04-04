package com.example.beachtest.Users.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.beachtest.R
import kotlin.random.Random

class MealReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: "Meal Reminder"
        val message = intent?.getStringExtra("message") ?: "Time for your meal!"
        val notificationId = Random.nextInt() // Generate a random ID for each notification to ensure uniqueness

        context?.let {
            val builder = NotificationCompat.Builder(it, "meal_reminders")
                .setSmallIcon(R.drawable.ic_notification) // Ensure you have this icon.
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            NotificationManagerCompat.from(it).notify(notificationId, builder.build())
        }
    }
}