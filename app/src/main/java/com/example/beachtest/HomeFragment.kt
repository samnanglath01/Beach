package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Retrieve dining hall names from the database
        val diningHalls = databaseHelper.getDiningHalls()

        // Map the static buttons in the layout to the dynamic dining hall names
        val buttons = listOf(binding.beachsidehomebutton, binding.hillsidehomebutton, binding.parksidehomebutton)
        for (i in diningHalls.indices) {
            buttons[i].apply {
                text = diningHalls[i]
                setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToWeekDayFragment(diningHalls[i])
                    findNavController().navigate(action)
                }
            }
        }
    }
}