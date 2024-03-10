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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the Usercustomizepreferences class
        val menuQueryManager = Usercustomizepreferences()

        menuQueryManager.fetchUserPreferencesAndQueryMenu(
            onResult = { documentSnapshots ->
                documentSnapshots.forEach { snapshot ->
                    val itemName = snapshot.id // Assuming the document ID is the item name
                    Log.d("MenuQuery", "Menu Item: $itemName")
                }
            },
            onError = { exception ->
                Log.e("MenuQuery", "Error fetching menu items", exception)
            }
        )
    }

}