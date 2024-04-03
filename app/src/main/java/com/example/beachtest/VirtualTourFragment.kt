package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentVirtualTourBinding

class VirtualTourFragment : Fragment() {
    private lateinit var binding: FragmentVirtualTourBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVirtualTourBinding.inflate(inflater, container, false)
        return binding.root

    }
}