package com.example.beachtest

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment


class ParksideTourFragment : Fragment() {

    private var videoView: VideoView? = null
    private var mediaController: MediaController? = null

    // Luis Flores
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parkside_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoView = view.findViewById(R.id.videoView)

        if (mediaController == null) {
            mediaController = MediaController(context)
            mediaController!!.setAnchorView(videoView)
        }

        videoView?.apply {
            setMediaController(mediaController)
            setVideoURI(Uri.parse("android.resource://" + context?.packageName + "/" + R.raw.parksidevid))
            requestFocus()
            start()

            setOnErrorListener { _, _, _ ->
                Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                false
            }
        }
    }



}