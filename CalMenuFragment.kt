package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context


class FragmentB : Fragment() {

    private lateinit var textSelectedDate: MaterialTextView
    private lateinit var calendarView: CalendarView
    private lateinit var buttonHillMenu: Button
    private lateinit var buttonBeachMenu: Button
    private lateinit var buttonParkMenu: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calmenu, container, false)

        // Manually bind views
        textSelectedDate = view.findViewById(R.id.text_selected_date)
        calendarView = view.findViewById(R.id.calendar_view)
        buttonHillMenu = view.findViewById(R.id.button_hill_menu)
        buttonBeachMenu = view.findViewById(R.id.button_beach_menu)
        buttonParkMenu = view.findViewById(R.id.button_park_menu)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set listener for date selection
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val formattedDate = formatDate(calendar.time)
            textSelectedDate.text = formattedDate
        }

        // Set click listeners for menu buttons
        buttonHillMenu.setOnClickListener {

        }

        buttonBeachMenu.setOnClickListener {
            // Handle Beach menu button click
            // You can perform any action here, such as displaying the Beach menu
        }

        buttonParkMenu.setOnClickListener {
            // Handle Park menu button click
            // You can perform any action here, such as displaying the Park menu
        }
    }

    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return formatter.format(date)
    }


}
