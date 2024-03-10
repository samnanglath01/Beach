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
    // Use navArgs delegate to retrieve arguments safely
    private val args: WeekDayFragmentArgs by navArgs()
    private var _binding: FragmentWeekDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWeekDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHelper = DatabaseHelper.DatabaseHelper(requireContext())
        val days = databaseHelper.getDays()

        val buttons = listOf(
            binding.mondaybutton,
            binding.tuesdaybutton,
            binding.wednesdaybutton,
            binding.thursdaybutton,
            binding.fridaybutton,
            binding.saturdaybutton,
            binding.sundaybutton
        )

        // Adjust visibility and set up click listeners based on available days
        buttons.forEachIndexed { index, button ->
            if (index < days.size) {
                button.text = days[index]
                button.visibility = View.VISIBLE
                button.setOnClickListener {
                    val action = WeekDayFragmentDirections.actionWeekDayFragmentToMealTimeFragment(
                        diningHall = args.diningHall, // Use the received diningHall argument
                        day = days[index] // Pass the selected day
                    )
                    findNavController().navigate(action)
                }
            } else {
                // Hide unused buttons
                button.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




/*

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
    private var _binding: FragmentWeekDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWeekDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHelper = DatabaseHelper.DatabaseHelper(requireContext())
        val days = databaseHelper.getDays()

        setupDayButtons(days)
    }

    private fun setupDayButtons(days: List<String>) {
        val buttons = listOf(binding.mondaybutton, binding.tuesdaybutton, binding.wednesdaybutton, binding.thursdaybutton, binding.fridaybutton, binding.saturdaybutton, binding.sundaybutton)

        // Hide all buttons initially
        buttons.forEach { it.visibility = View.GONE }

        // Show and setup buttons based on available days
        days.forEachIndexed { index, day ->
            if (index < buttons.size) {
                buttons[index].apply {
                    visibility = View.VISIBLE
                    text = day
                    setOnClickListener {
                        val action = WeekDayFragmentDirections.actionWeekDayFragmentToMealTimeFragment(args.diningHall, day)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
*/