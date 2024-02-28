package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentHomeBinding
import com.example.beachtest.databinding.FragmentWeekDayBinding
import com.example.beachtest.databinding.FragmentMealTimeBinding

class WeekDayFragment : Fragment() {
    private lateinit var binding: FragmentWeekDayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeekDayBinding.inflate(inflater, container, false)

        binding.mondayButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekDayFragment_to_mealTimeFragment)
        }

        binding.tuesdayButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekDayFragment_to_mealTimeFragment)
        }

        binding.wednesdayButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekDayFragment_to_mealTimeFragment)
        }

        binding.thursdayButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekDayFragment_to_mealTimeFragment)
        }

        binding.fridayButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekDayFragment_to_mealTimeFragment)
        }

        binding.saturdayButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekDayFragment_to_mealTimeFragment)
        }

        binding.sundayButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekDayFragment_to_mealTimeFragment)
        }
        return binding.root
    }

}