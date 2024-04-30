package com.example.beachtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

class LanguagePrefUiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_language_pref_ui, container, false)

        displayCurrentLanguage(view)

        return view
    }

    private fun displayCurrentLanguage(view: View) {
        // Using Locale.getDefault() directly
        val currentLocale = Locale.getDefault()
        val displayName = currentLocale.displayName // This should give you a more user-friendly language name

        // Ensure this TextView exists in your layout and is correctly referenced
        view.findViewById<TextView>(R.id.currentLanguageTextView).text = getString(R.string.current_language, displayName)
    }
}
