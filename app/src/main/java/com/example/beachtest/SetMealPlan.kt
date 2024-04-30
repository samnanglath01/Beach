package com.example.beachtest

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beachtest.Users.ui.MealReminderReceiver

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetMealPlan.newInstance] factory method to
 * create an instance of this fragment.
 */
//samnang lath
class SetMealPlan : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    //Samnang Lath

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_meal_plan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backHomeButton).setOnClickListener {
            findNavController().navigate(R.id.action_setMealPlan_to_homePageFragment)
        }
        view.findViewById<Button>(R.id.morningMealbutton).setOnClickListener {
            if (checkAndRequestExactAlarmPermission()) {
                scheduleMealReminder(requireContext(), "Breakfast")
                showConfirmationDialog("Breakfast")
            }
        }

        view.findViewById<Button>(R.id.lunchbutton).setOnClickListener {
            if (checkAndRequestExactAlarmPermission()) {
                scheduleMealReminder(requireContext(), "Lunch")
                showConfirmationDialog("Lunch")
            }
        }

        view.findViewById<Button>(R.id.dinnerbutton).setOnClickListener {
            if (checkAndRequestExactAlarmPermission()) {
                scheduleMealReminder(requireContext(), "Dinner")
                showConfirmationDialog("Dinner")
            }
        }

    }
    private fun showConfirmationDialog(mealType: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Booking Confirmed")
            .setMessage("You have successfully booked your $mealType plan.")
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun checkAndRequestExactAlarmPermission(): Boolean {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
            return false // Permission not granted, so don't proceed to schedule reminder
        }
        return true // Permission granted or not required, safe to proceed
    }
    private fun scheduleMealReminder(context: Context, mealType: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MealReminderReceiver::class.java).apply {
            putExtra("title", "$mealType Meal Reminder")
            putExtra("message", "Time for your $mealType!")
        }
        val pendingIntent = PendingIntent.getBroadcast(context, mealType.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, when(mealType) {
                "Breakfast" -> 8
                "Lunch" -> 12
                "Dinner" -> 18
                else -> 8 // Default to 8 AM
            })
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)

            // Schedule for the next day if the time has already passed
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
    private fun getNotificationIdForMeal(mealType: String): Int {
        return mealType.hashCode() // Convert meal type string to a consistent hash code
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetMealPlan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            SetMealPlan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}