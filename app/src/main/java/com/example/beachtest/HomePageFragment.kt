package com.example.beachtest

// Import statements to include necessary classes from the Android framework and the Material Design components.
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageFragment : Fragment() {
    // Declaration of a BottomNavigationView variable that will be initialized later.
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // This method inflates the layout for this fragment.
        // The layout file 'fragment_home_page.xml' is turned into a View object which can be rendered by the app.
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // This callback is invoked after the fragment's view has been created.
        super.onViewCreated(view, savedInstanceState)

        // Initialize the BottomNavigationView object by finding it within the fragment's View hierarchy.
        bottomNavigationView = view.findViewById(R.id.bottom_navigation)

        // If savedInstanceState is null, it means this is the first time the fragment is created, not recreated from a saved state.
        if (savedInstanceState == null) {
            // Set the initial fragment to HomeFragment when the fragment is first created.
            replaceFragment(HomeFragment())
        }

        // Set up a listener for item selection events on the BottomNavigationView.
        bottomNavigationView.setOnItemSelectedListener { item ->
            // Determine which menu item was selected.
            when (item.itemId) {
                // If the 'home' item is selected, replace the current fragment with HomeFragment.
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true // Return true to display the item as the selected item.
                }
                // If the 'options' item is selected, replace the current fragment with OptionsFragment.
                R.id.bottom_options -> {
                    replaceFragment(OptionsFragment())
                    true // Return true to display the item as the selected item.
                }
                // If neither of the above, do not handle the selection.
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // This method replaces the current fragment displayed in the 'frame_container' with the provided fragment.
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, fragment)
            commit() // Commit the transaction to apply the change.
        }
    }
}
