package com.example.beachtest

// Required imports for various Android and Jetpack components
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentPhotographyTipBinding

// PhotographyTipFragment class definition that extends Fragment
class PhotographyTipFragment : Fragment() {

    // Declaration of a binding variable to handle the fragment's views
    private lateinit var binding: FragmentPhotographyTipBinding

    // Nullable MediaController for controlling media playback
    private var mediaController: MediaController? = null

    // onCreateView callback to inflate the fragment's layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentPhotographyTipBinding.inflate(inflater, container, false)

        // Set an OnClickListener for the back button to navigate to the HomePageFragment
        binding.backhomebutton.setOnClickListener {
            // Navigate back to the HomePageFragment using NavController
            it.findNavController().navigate(R.id.action_photographyTipFragment_to_homePageFragment)
        }

        // Return the root view of the binding to display the fragment UI
        return binding.root
    }

    // onViewCreated callback to perform further initialization once the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize MediaController and set it to anchor on the VideoView
        mediaController = MediaController(context).apply {
            setAnchorView(binding.videoView)
        }

        // Configure the VideoView to use the MediaController, set the video URI, and start playback
        binding.videoView.apply {
            setMediaController(mediaController)
            // Set video URI pointing to a raw resource named foodphotography
            setVideoURI(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.foodphotography))
            requestFocus() // Request focus to start playback
            start() // Start video playback

            // Set a completion listener to display a toast message when the video ends
            setOnCompletionListener {
                Toast.makeText(context, "Video End", Toast.LENGTH_LONG).show()
            }

            // Set an error listener to handle playback errors
            setOnErrorListener { _, _, _ ->
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
                false // Return false indicating the error was not handled here
            }
        }
    }
}
