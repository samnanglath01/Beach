package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentScheduleMealsBinding
import java.util.Calendar
import java.util.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import com.google.firebase.firestore.FieldValue

class ScheduleMealsFragment : Fragment() {

    private var _binding: FragmentScheduleMealsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleMealsBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.backHomeButton.setOnClickListener {
            Toast.makeText(context, "Back to Options clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_scheduleMealsFragment_to_homePageFragment)
        }

        binding.datePickerButton.setOnClickListener {
            Toast.makeText(context, "Pick Date clicked", Toast.LENGTH_SHORT).show()
            showDatePickerDialog()
        }

        binding.timePickerButton.setOnClickListener {
            Toast.makeText(context, "Pick Time clicked", Toast.LENGTH_SHORT).show()
            showTimePickerDialog()
        }

        binding.notificationToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(context, "Notifications for meal scheduling turned ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Notifications for meal scheduling turned OFF", Toast.LENGTH_SHORT).show()
            }
        }

        binding.scheduleMealButton.setOnClickListener {
            val mealDescription = binding.mealDescription.text.toString()
            val selectedDate = binding.selectedDateText.text.toString()
            val selectedTime = binding.selectedTimeText.text.toString()
            val mealType = binding.mealTypeSpinner.selectedItem.toString()

            if (mealDescription.isNotEmpty() && selectedDate != "No date selected" && selectedTime != "No time selected") {
                saveMealToFirestore(mealDescription, selectedDate, selectedTime, mealType)
            } else {
                Toast.makeText(context, "Please fill in all details.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            binding.selectedDateText.text = getString(R.string.date_format, dayOfMonth, month + 1, year)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            binding.selectedTimeText.text = timeFormat.format(calendar.time)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
    }

    private fun isUserLoggedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }
    private fun saveMealToFirestore(mealDescription: String, date: String, time: String, mealType: String) {
        // Get the user ID
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        if (!isUserLoggedIn()) {
            Toast.makeText(context, "Please log in to schedule a meal.", Toast.LENGTH_LONG).show()
            // Redirect to login page or show login prompt
            return
        }

        // Construct a custom document name (for example, concatenating user ID and current timestamp)
        val customDocumentName = "$userId-${System.currentTimeMillis()}"


        val mealDetails = hashMapOf(
            "userId" to userId,
            "mealDescription" to mealDescription,
            "mealDate" to date,
            "mealTime" to time,
            "mealType" to mealType,
            "createdTimestamp" to FieldValue.serverTimestamp()
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("scheduledMeals")
            .add(mealDetails)
            .addOnSuccessListener {
                Toast.makeText(context, "Meal scheduled successfully!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error scheduling meal: $e", Toast.LENGTH_LONG).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}