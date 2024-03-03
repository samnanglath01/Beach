package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentWeekDayBinding
import androidx.navigation.fragment.navArgs

class WeekDayFragment : Fragment() {
    private val args: WeekDayFragmentArgs by navArgs()
    private lateinit var binding: FragmentWeekDayBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWeekDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHelper = DatabaseHelper.DatabaseHelper(requireContext())

        val days = databaseHelper.getDays()

        val container = view.findViewById<LinearLayout>(R.id.daysContainer)

        days.forEach { day ->
            val button = Button(requireContext()).apply {
                text = day
                setOnClickListener {
                    val action = WeekDayFragmentDirections.actionWeekDayFragmentToMealTimeFragment(diningHall = args.diningHall, day = day)
                    findNavController().navigate(action)
                }
            }
            container?.addView(button)
        }
    }

}