package com.example.beachtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView

class MapFrag : Fragment() {
    //UI NEEDS TO BE CHANGED
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        rootView.findViewById<Button>(R.id.buttonNuggetGrillPub).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/7inJZp6FWFfLPRsB8")
        }
        rootView.findViewById<Button>(R.id.buttonOutpostGrill).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/B9v7kC6HQg45nfJm8")
        }
        rootView.findViewById<Button>(R.id.buttonBeachsideDining).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/vKKZEajKz3uZfMAx6")
        }
        rootView.findViewById<Button>(R.id.buttonHillsideDining).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/V1npaRWecxnLKNTTA")
        }
        rootView.findViewById<Button>(R.id.buttonParksideDining).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/Eo9oAmBFJER2jqaf7")
        }
        rootView.findViewById<Button>(R.id.buttonUsuDining).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/3jKcq7Y82Wv87aaj6")
        }
        rootView.findViewById<TextView>(R.id.buttonUsuDining).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/3jKcq7Y82Wv87aaj6")
        }
        rootView.findViewById<TextView>(R.id.buttonSubway).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/3jKcq7Y82Wv87aaj6")
        }
        rootView.findViewById<TextView>(R.id.buttonCoffeeBean).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/3jKcq7Y82Wv87aaj6")
        }
        rootView.findViewById<TextView>(R.id.buttonRobeks).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/3jKcq7Y82Wv87aaj6")
        }
        rootView.findViewById<TextView>(R.id.buttonElPollo).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/3jKcq7Y82Wv87aaj6")
        }
        rootView.findViewById<TextView>(R.id.buttonCarlsJr).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/3jKcq7Y82Wv87aaj6")
        }
        rootView.findViewById<TextView>(R.id.buttonCornerMarket).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/FAgbk9LDNnRj8KmeA")
        }
        rootView.findViewById<TextView>(R.id.buttonCafeLab).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/veLYRi95XXbVhYHN6")
        }
        rootView.findViewById<TextView>(R.id.buttonStudentStore).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/Qk67LPMtqYJv2E2Z6")
        }
        rootView.findViewById<TextView>(R.id.buttonBeachHut).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/Md1YAAExfnUFBmzUA")
        }
        rootView.findViewById<TextView>(R.id.buttonArtStore).setOnClickListener {
            openWebPage("https://maps.app.goo.gl/mumnsQYd7QxzCSN49")
        }

        return rootView
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}
