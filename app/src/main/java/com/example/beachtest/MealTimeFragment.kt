package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentMealTimeBinding
import androidx.navigation.fragment.navArgs


class MealTimeFragment : Fragment() {
    private val args: MealTimeFragmentArgs by navArgs()

    private lateinit var binding: FragmentMealTimeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMealTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHelper = DatabaseHelper.DatabaseHelper(requireContext())

        val mealtimes = databaseHelper.getMealTimes()

        val container = view.findViewById<LinearLayout>(R.id.mealtimesContainer)

        mealtimes.forEach { mealTime ->
            val button = Button(requireContext()).apply {
                text = mealTime
                setOnClickListener {
                    val action = MealTimeFragmentDirections.actionMealTimeFragmentToMenuItemsFragment(
                        diningHall = args.diningHall,
                        day = args.day,
                        mealTime = mealTime)
                    findNavController().navigate(action)
                }
            }
            container?.addView(button)
        }
    }
}
