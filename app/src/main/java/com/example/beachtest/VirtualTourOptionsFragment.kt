package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentVirtualTourOptionsBinding

class VirtualTourOptionsFragment : Fragment() {
    private lateinit var binding: FragmentVirtualTourOptionsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVirtualTourOptionsBinding.inflate(inflater, container, false)
        return binding.root

    }
}