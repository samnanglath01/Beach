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
        rootView.findViewById<TextView>(R.id.textVendor1).setOnClickListener {
            openWebPage("https://www.5elementosla.com")
        }
        rootView.findViewById<TextView>(R.id.textVendor2).setOnClickListener {
            openWebPage("https://www.babysbadassburgers.com")
        }
        rootView.findViewById<TextView>(R.id.textVendor3).setOnClickListener {
            openWebPage("https://www.sugoitaliano.com")
        }
        rootView.findViewById<TextView>(R.id.textVendor4).setOnClickListener {
            openWebPage("https://www.crepesbonaparte.com")
        }
        rootView.findViewById<TextView>(R.id.textVendor5).setOnClickListener {
            openWebPage("https://www.smilehotdog.com")
        }

        rootView.findViewById<TextView>(R.id.backhomebutton).setOnClickListener {
            it.findNavController().navigate(R.id.action_foodTruckVendorDetailsFragment_to_foodTrucksFragment)
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, "No application can handle this request. Please install a web browser or check the URL.", Toast.LENGTH_LONG).show()
        }
    }
}
