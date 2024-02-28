package com.example.beachtest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Launch a coroutine
        CoroutineScope(Dispatchers.Main).launch {
            // Delay for 3 seconds
            delay(3000)
            // Navigate to SignInFragment after delay
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }

        return view
    }
}
