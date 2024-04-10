package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

/**
 * SplashFragment serves as the initial screen users see upon app launch.
 * It displays a splash screen for a brief period before navigating to the SignInFragment.
 * This approach enhances user experience by providing a visually appealing entry point to the app.
 * Written by: Marlen Dizon
 */
class SplashFragment : Fragment() {
    /**
     * Called to have the fragment instantiate its user interface view.
     * Here, it inflates the splash screen layout and initiates a coroutine for the delay.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment, setting up the initial UI
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Launch a coroutine in the main thread to handle UI-related tasks
        CoroutineScope(Dispatchers.Main).launch {
            // Delay for 3 seconds to show the splash screen
            delay(3000)
            // After the delay, navigate to the SignInFragment to allow user authentication
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }

        return view
    }
}
