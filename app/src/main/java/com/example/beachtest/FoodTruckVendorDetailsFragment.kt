package com.example.beachtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class FoodTruckVendorDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_food_truck_vendor_details, container, false)
        setupTextViewListeners(rootView)
        return rootView
    }

    private fun setupTextViewListeners(rootView: View) {
        val vendorIds = listOf(
            R.id.textVendor1 to "https://www.5elementosla.com",
            R.id.textVendor2 to "https://www.babysbadassburgers.com",
            R.id.textVendor3 to "https://www.sugoitaliano.com",
            R.id.textVendor4 to "https://www.crepesbonaparte.com",
            R.id.textVendor5 to "https://www.smilehotdog.com",
            R.id.textVendor6 to "https://www.tacotherapysd.com/",
            R.id.textVendor7 to "https://www.instagram.com/turkishgrillfarmersmarket",
            R.id.textVendor8 to "http://www.lalalasagna.com/",
            R.id.textVendor9 to "https://www.kalamakigreekla.com"
        )

        vendorIds.forEach { (id, url) ->
            rootView.findViewById<TextView>(id).setOnClickListener {
                openWebPage(url)
            }
        }

        rootView.findViewById<TextView>(R.id.backhomebutton).setOnClickListener {
            it.findNavController().navigate(R.id.action_foodTruckVendorDetailsFragment_to_foodTrucksFragment)
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No application can handle this request. Please install a web browser.", Toast.LENGTH_LONG).show()
        }
    }
}