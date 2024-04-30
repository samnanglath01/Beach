package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Coordinates for CSULB
        val csulb = LatLng(33.7838, -118.1141)
        mMap.addMarker(MarkerOptions().position(csulb).title("CSULB"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(csulb, 15f))

        // Setup buttons and actions for food locations
        setupFoodLocations()
    }

    private fun setupFoodLocations() {
        view?.findViewById<View>(R.id.btnSubway)?.setOnClickListener {
            addMarkerAt(33.7825, -118.1154, "Subway")
        }
        view?.findViewById<View>(R.id.btnElPolloLoco)?.setOnClickListener {
            addMarkerAt(33.7830, -118.1120, "El Pollo Loco")
        }
        view?.findViewById<View>(R.id.btnCoffeeBeach)?.setOnClickListener {
            addMarkerAt(33.7832, -118.1122, "Coffee Beach")
        }
    }

    private fun addMarkerAt(lat: Double, lng: Double, title: String) {
        val location = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(location).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
    }
}
