package com.example.beachtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController

class FoodTruckVendorDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_truck_vendor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonListeners(view)

    }

    private fun setupButtonListeners(view: View) {
        view.findViewById<Button>(R.id.buttonVendor1)?.setOnClickListener {
            openWebPage("https://www.5elementosla.com/")
        }
        view.findViewById<Button>(R.id.buttonVendor2)?.setOnClickListener {
            openWebPage("https://www.babysbadassburgers.com/")
        }
        view.findViewById<Button>(R.id.buttonVendor3)?.setOnClickListener {
            openWebPage("http://www.sugoitaliano.com/")
        }
        view.findViewById<Button>(R.id.buttonVendor4)?.setOnClickListener {
            openWebPage("http://www.crepesbonaparte.com/")
        }
        view.findViewById<Button>(R.id.buttonVendor5)?.setOnClickListener {
            openWebPage("http://www.smilehotdog.com/")
        }

        // Set up click listener for back button
        view.findViewById<Button>(R.id.backhomebutton)?.setOnClickListener {
            // Navigate back to the FoodTrucksFragment
            it.findNavController()
                .navigate(R.id.action_foodTruckVendorDetailsFragment_to_foodTrucksFragment)
        }
    }

    private fun openWebPage(url: String) {
        try {
            val formattedUrl =
                if (url.startsWith("http://") || url.startsWith("https://")) url else "http://$url"
            val webPage: Uri = Uri.parse(formattedUrl)
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            // Check if there's an app that can handle this intent
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                // Log an error or show a toast
                Log.e(
                    "FoodTruckDetailsFragment",
                    "No application can handle this request. Please install a web browser or check the URL."
                )
                Toast.makeText(
                    context,
                    "No application can handle this request.",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Log.e("FoodTruckDetailsFragment", "Failed to open URL: $url", e)
            // Optionally handle the error or inform the user
        }
    }
}
