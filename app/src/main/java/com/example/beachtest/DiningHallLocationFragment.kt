package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentDiningHallLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// Fragment subclass to display dining hall locations on a map.
class DiningHallLocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap // Lateinit property for the GoogleMap.
    private var _binding: FragmentDiningHallLocationBinding? = null // Nullable property for view binding.
    private val binding get() = _binding!! // Non-nullable getter for view binding.

    // Inflates the layout for this fragment with view binding.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDiningHallLocationBinding.inflate(inflater, container, false)
        // Sets up a click listener on the back button to navigate back to the home page.
        binding.backhomebutton.setOnClickListener {
            Toast.makeText(context, "Profile clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_diningHallLocationFragment_to_homePageFragment)
        }
        return binding.root // Returns the root view from the binding object.
    }

    // After the view is created, set up the map.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Prepare the MapView with the lifecycle of the fragment.
        binding.userListMap.onCreate(savedInstanceState)
        // Asynchronously get the map. When it's ready, onMapReady will be called.
        binding.userListMap.getMapAsync(this)

        // Set click listeners for the logos to navigate the map to their respective coordinates.
        binding.hillsideLogo.setOnClickListener { navigateTo(LatLng(33.7831, -118.1122)) }
        binding.parksideLogo.setOnClickListener { navigateTo(LatLng(33.7838, -118.1159)) }
        binding.beachsideLogo.setOnClickListener { navigateTo(LatLng(33.7772, -118.1481)) }
    }

    // Method to animate the camera to a specific latitude and longitude on the map.
    private fun navigateTo(location: LatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
    }

    // Callback when the map is ready to be used.
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap // Assign the map to the mMap property.

        // Add markers to the map at the specified coordinates for different dining halls.
        val parksideDining = LatLng(33.7838, -118.1159)
        mMap.addMarker(MarkerOptions().position(parksideDining).title("Parkside Dining Hall"))

        val hillsideDining = LatLng(33.7831, -118.1122)
        mMap.addMarker(MarkerOptions().position(hillsideDining).title("Hillside Dining Hall"))

        val beachsideDining = LatLng(33.7772, -118.1481)
        mMap.addMarker(MarkerOptions().position(beachsideDining).title("Beachside Dining Hall"))

        // Center the map to a position that is likely to display all markers.
        val centerPoint = LatLng(33.7814, -118.1154)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPoint, 14f))
    }

    // Lifecycle methods to manage the MapView.
    override fun onResume() {
        super.onResume()
        binding.userListMap.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.userListMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.userListMap.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.userListMap.onPause()
    }

    // Clean up the view binding when the fragment is destroyed.
    override fun onDestroy() {
        super.onDestroy()
        binding.userListMap.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.userListMap.onLowMemory()
    }

    // Save the current state of the map before the fragment is destroyed.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.userListMap.onSaveInstanceState(outState)
    }
}