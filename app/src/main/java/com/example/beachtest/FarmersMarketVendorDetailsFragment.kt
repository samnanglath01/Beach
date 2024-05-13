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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmers_market_vendor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonListeners(view)
    }

    private fun setupButtonListeners(view: View) {
        val vendorUrls = mapOf(
            R.id.buttonVendor1 to "https://www.theareparepublic.com/",
            R.id.buttonVendor2 to "https://www.tacomasa.com/",
            R.id.buttonVendor3 to "https://www.instagram.com/la_baobaohouse/",
            R.id.buttonVendor4 to "https://cuebakeshop.com/",
            R.id.buttonVendor5 to "https://salvadorenorestaurant.com/"
        )

        vendorUrls.forEach { (buttonId, url) ->
            view.findViewById<Button>(buttonId)?.setOnClickListener {
                openWebPage(url)
            }
        }

        view.findViewById<Button>(R.id.backhomebutton)?.setOnClickListener {
            it.findNavController().navigate(R.id.action_farmersMarketVendorDetailsFragment_to_foodTrucksFragment)
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE) // Ensure this Intent is handled by a web browser
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("FarmersMarketVendorDetailsFragment", "No application can handle this request. URL: $url", e)
            Toast.makeText(requireContext(), "No application can handle this request. Please install a web browser.", Toast.LENGTH_LONG).show()
        }
    }
}
