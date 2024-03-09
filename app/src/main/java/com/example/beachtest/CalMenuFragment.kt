package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beachtest.databinding.FragmentCalMenuBinding
import java.text.SimpleDateFormat
import java.util.*
import android.widget.CalendarView

//UI NEEDS TO BE CHANGED
class CalMenuFragment : Fragment() {

    private var _binding: FragmentCalMenuBinding? = null
    private val binding get() = _binding!!
    private var globalFormattedDate: String? = null
    private var global: Int? = null
    private var menuItemsLoaded = false

    val dayToNumericMap = mapOf(
        "Sunday" to 1,
        "Monday" to 2,
        "Tuesday" to 3,
        "Wednesday" to 4,
        "Thursday" to 5,
        "Friday" to 6,
        "Saturday" to 7
    )
    val test = mapOf(
        1 to "Sunday",
        2 to "Monday",
        3 to "Tuesday",
        4 to "Wednesday",
        5 to "Thursday",
        6 to "Friday",
        7 to "Saturday"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the CalendarView listener
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val formattedDate = formatDate(calendar.time)
            globalFormattedDate = formattedDate
            global = dayToNumericMap[formattedDate]
            binding.textSelectedDate.text = formattedDate

            // Reset menuItemsLoaded flag when date changes
            menuItemsLoaded = false
        }

        // Bind button click listener
        // Attach the click listeners here
        binding.buttonHillMenu.setOnClickListener { loadMenuItems("Hillside") }
        binding.buttonParkMenu.setOnClickListener { loadMenuItems("Parkside") }
        binding.buttonBeachMenu.setOnClickListener { loadMenuItems("Beachside") }
    }

    private fun loadMenuItems(diningHall: String) {
        val queryDay: String? = test?.get(global)?.toString()
        val dbHelper = DatabaseHelper.DatabaseHelper(requireContext())
        dbHelper.populateInitialData()

        val menuItems = dbHelper.getCalMenuItems(queryDay.toString(), diningHall)

        if (menuItems.isNullOrEmpty()) {
            binding.textMenu.text = "No items"
        } else {
            // Display the menu items in the TextView
            binding.textMenu.text = menuItems.joinToString("\n")
        }

        // Set the flag to indicate that menu items have been loaded
        menuItemsLoaded = true
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return formatter.format(date)
    }
}