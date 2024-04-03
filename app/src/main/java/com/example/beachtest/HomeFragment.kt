package com.example.beachtest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beachtest.Users.ui.Usercustomizepreferences

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    // This method is called immediately after the fragment's view has been created.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the Usercustomizepreferences class
        // Initialize an instance of Usercustomizepreferences class to manage menu queries based on user preferences
        val menuQueryManager = Usercustomizepreferences()
        // Call fetchUserPreferencesAndQueryMenu on the menuQueryManager instance.
        // This function fetches user preferences and queries the menu accordingly.

        menuQueryManager.fetchUserPreferencesAndQueryMenu(
            onResult = { documentSnapshots ->
                // This lambda is executed if the query is successful.
                // It iterates through each documentSnapshot representing a menu item.
                documentSnapshots.forEach { snapshot ->
                    // Retrieve the document ID of each snapshot, assuming it's the item name.
                    val itemName = snapshot.id // Assuming the document ID is the item name
                    Log.d("MenuQuery", "Menu Item: $itemName")
                }
            },
            onError = { exception ->
                // This lambda is executed if there's an error during the query.
                // Log the error message along with the exception.
                Log.e("MenuQuery", "Error fetching menu items", exception)
            }
            //end of the sam's code
        )
    }

}