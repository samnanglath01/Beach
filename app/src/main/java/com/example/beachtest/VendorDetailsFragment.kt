package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class VendorDetailsFragment : Fragment() {

    companion object {
        private const val ARG_VENDOR_TYPE = "vendorType"

        fun newInstance(vendorType: String) =
            VendorDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_VENDOR_TYPE, vendorType)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vendor_details, container, false)
        val vendorType = arguments?.getString(ARG_VENDOR_TYPE)
        val titleTextView: TextView = view.findViewById(R.id.vendorTypeTextView) // Assuming you add this TextView to your layout
        titleTextView.text = vendorType
        return view
    }
}
