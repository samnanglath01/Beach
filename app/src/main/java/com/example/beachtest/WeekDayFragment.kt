package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val buttons = listOf(binding.mondaybutton, binding.tuesdaybutton, binding.wednesdaybutton, binding.thursdaybutton, binding.fridaybutton, binding.saturdaybutton, binding.sundaybutton)

        // Map buttons to days of the week and set click listeners
        days.forEachIndexed { index, day ->
            buttons[index].apply {
                text = day
                setOnClickListener {
                    val action = WeekDayFragmentDirections.actionWeekDayFragmentToMealTimeFragment(args.diningHall, day)
                    findNavController().navigate(action)
                }
            }
        }

        // Hide any buttons that do not correspond to a day from the database
        for (i in days.size until buttons.size) {
            buttons[i].visibility = View.GONE
        }
    }
}

/*days.forEach { day ->
            val button = Button(requireContext()).apply {
                text = day
                setOnClickListener {
                    val action = WeekDayFragmentDirections.actionWeekDayFragmentToMealTimeFragment(diningHall = args.diningHall, day = day)
                    findNavController().navigate(action)
                }
            }
            container?.addView(button)
        }*/