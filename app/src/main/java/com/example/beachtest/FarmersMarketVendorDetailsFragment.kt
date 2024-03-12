package com.example.beachtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class FarmersMarketVendorDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmers_market_vendor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonListeners(view)
    }

    private fun setupButtonListeners(view: View) {
        view.findViewById<Button>(R.id.buttonVendor1)?.setOnClickListener {
            openWebPage("https://www.theareparepublic.com/")
        }
        view.findViewById<Button>(R.id.buttonVendor2)?.setOnClickListener {
            openWebPage("https://www.tacomasa.com/")
        }
        view.findViewById<Button>(R.id.buttonVendor3)?.setOnClickListener {
            openWebPage("https://www.instagram.com/la_baobaohouse/")
        }
        view.findViewById<Button>(R.id.buttonVendor4)?.setOnClickListener {
            openWebPage("https://cuebakeshop.com/")
        }
        view.findViewById<Button>(R.id.buttonVendor5)?.setOnClickListener {
            openWebPage("https://salvadorenorestaurant.com/")
        }
        // Set up click listener for back button
        view.findViewById<Button>(R.id.backhomebutton)?.setOnClickListener {
            // Navigate back to the FoodTrucksFragment
            it.findNavController()
                .navigate(R.id.action_farmersMarketVendorDetailsFragment_to_foodTrucksFragment)
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
                    "FarmersMarketVendorDetailsFragment",
                    "No application can handle this request. Please install a web browser or check the URL."
                )
                Toast.makeText(
                    requireContext(),
                    "No application can handle this request.",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Log.e("FarmersMarketVendorDetailsFragment", "Failed to open URL: $url", e)
            // Optionally handle the error or inform the user
        }
    }
}
