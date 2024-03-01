package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentDietaryPreferenceBinding
import com.example.beachtest.databinding.FragmentSignInBinding

class DietaryPreferenceFragment : Fragment() {

    private lateinit var binding: FragmentDietaryPreferenceBinding

            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDietaryPreferenceBinding.inflate(inflater, container, false)

        binding.saveDietbutton.setOnClickListener {
            it.findNavController().navigate(R.id.action_dietaryPreferenceFragment_to_allergyFragment)
        }

        return binding.root
    }

}