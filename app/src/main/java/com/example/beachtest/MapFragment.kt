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

// Luis Flores, food around campus
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
        view?.findViewById<View>(R.id.btnNugget)?.setOnClickListener {
            addMarkerAt(33.7803, -118.1141, "The Nugget")
        }
        view?.findViewById<View>(R.id.btnOutpost)?.setOnClickListener {
            addMarkerAt(33.7823, -118.1105, "Outpost Grill")
        }
        view?.findViewById<View>(R.id.btnCaffeineLab)?.setOnClickListener {
            addMarkerAt(33.7767, -118.1140, "Caffeine Lab")
        }
        view?.findViewById<View>(R.id.btnSubway)?.setOnClickListener {
            addMarkerAt(33.7810, -118.1138, "Subway")
        }
        view?.findViewById<View>(R.id.btnElPolloLoco)?.setOnClickListener {
            addMarkerAt(33.7811, -118.1138, "El Pollo Loco")
        }
        view?.findViewById<View>(R.id.btnCarlsJr)?.setOnClickListener {
            addMarkerAt(33.7811, -118.1137, "Carl's Jr.")
        }
        view?.findViewById<View>(R.id.btnCoffeeBean)?.setOnClickListener {
            addMarkerAt(33.7810, -118.1134, "The Coffee Bean")
        }
    }

    private fun addMarkerAt(lat: Double, lng: Double, title: String) {
        val location = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(location).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
    }
}
