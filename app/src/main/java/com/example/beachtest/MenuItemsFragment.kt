package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.beachtest.databinding.FragmentMenuItemsBinding

class MenuItemsFragment : Fragment() {
    private var _binding: FragmentMenuItemsBinding? = null
    private val binding get() = _binding!!
    private val args: MenuItemsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayMenuItems()
    }

    private fun displayMenuItems() {
        val databaseHelper = DatabaseHelper.DatabaseHelper(requireContext())
        val menuItems = databaseHelper.getMenuItems(args.day, args.mealTime, args.diningHall)

        menuItems.forEach { menuItem ->
            val textView = TextView(context).apply {
                text = menuItem
                textSize = 18f
                // Add any additional styling you need here
            }
            binding.menuItemsContainer.addView(textView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}