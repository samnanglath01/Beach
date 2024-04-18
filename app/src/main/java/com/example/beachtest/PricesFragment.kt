package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentPricesBinding

class PricesFragment : Fragment() {

    private lateinit var binding: FragmentPricesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPricesBinding.inflate(inflater, container, false)

        // Set up click listener for the back home button
        binding.backHomeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_pricesFragment_to_optionsFragment)
        }

        return binding.root
    }
}
