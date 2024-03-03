package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHelper = DatabaseHelper.DatabaseHelper(requireContext())

        val diningHalls = databaseHelper.getDiningHalls()

        val container = view.findViewById<LinearLayout>(R.id.container)

        diningHalls.forEach { diningHall ->
            val button = Button(requireContext()).apply {
                text = diningHall
                setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToWeekDayFragment(diningHall)
                    findNavController().navigate(action)
                }
            }
            container?.addView(button)
        }
    }
}