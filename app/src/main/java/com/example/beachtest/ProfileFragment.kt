
package com.example.beachtest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.beachtest.Users.ui.UserManager
import com.example.beachtest.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
// Samnang Lath
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.backhomebutton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_homePageFragment)
        }
        binding.editButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.editDietButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editDietaryPreferenceFragment)
        }
        //samnang lath
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val navController = it.findNavController()

            // Assuming signInFragment is accessible directly without popping anything
            navController.navigate(R.id.signInFragment)

            // Clear all back stack after navigation
            navController.popBackStack(R.id.splashFragment, false) // Replace 'startDestination' with the ID of the root or start destination in your navigation graph
        }

        binding.backHomeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_homePageFragment)
        }

        //samnang lath
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val navController = it.findNavController()

            // Assuming signInFragment is accessible directly without popping anything
            navController.navigate(R.id.signInFragment)

            // Clear all back stack after navigation
            navController.popBackStack(R.id.splashFragment, false) // Replace 'startDestination' with the ID of the root or start destination in your navigation graph
        }

        //samnang lath
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val navController = it.findNavController()

            // Assuming signInFragment is accessible directly without popping anything
            navController.navigate(R.id.signInFragment)

            // Clear all back stack after navigation
            navController.popBackStack(R.id.splashFragment, false) // Replace 'startDestination' with the ID of the root or start destination in your navigation graph
        }


        return binding.root
    }
    //Samnang Lath
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Initialize UserManager
        val userManager = UserManager()

        // Assuming you have a TextView with the ID profileUsername in your fragment's layout
        val usernameTextView = view.findViewById<TextView>(R.id.profileUsername)
        val emailTextView = view.findViewById<TextView>(R.id.profileEmail)

        // Fetch the current user's username and update the TextView
        userManager.getCurrentUsername { username ->
            // This block of code will run after the username is retrieved
            activity?.runOnUiThread {
                // Make sure you are on the UI thread when updating UI components
                usernameTextView.text = username ?: "Username not found"
            }
        }
        userManager.getCurrentUserEmail { email ->
            activity?.runOnUiThread {
                emailTextView.text = email ?: "Email not found"
            }
        }
        val profileButton: Button = view.findViewById(R.id.btnProfile)
        val profileImageView: ImageView = view.findViewById(R.id.profileImg)
        val btnDelete:TextView= view.findViewById(R.id.DeleteButton)
        profileButton.setOnClickListener {
            // Check for permissions and open gallery
            openGallery()
        }
        btnDelete.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser

            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(), "Account deleted.", Toast.LENGTH_SHORT).show()

                    it.findNavController().navigate(R.id.action_profileFragment_to_signInFragment)

                    // Handle the post-account-deletion logic here, such as navigating to a sign-in activity.
                } else {
                    Toast.makeText(requireActivity(), "Failed to delete account.", Toast.LENGTH_SHORT).show()
                    // Handle the error, possibly asking the user to try again.
                }
            }
        }
    }
    //Samnang Lath
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // Handle the image chosen by the user
            val selectedImageUri: Uri? = data?.data
            view?.findViewById<ImageView>(R.id.profileImg)?.setImageURI(selectedImageUri)
        }
    }
    companion object {
        private const val IMAGE_PICK_CODE = 999 // Request code for picking an image
        private const val PERMISSION_CODE = 1001 // Request code for permissions
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    //Samnang Lath
    private fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted, open the gallery
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, IMAGE_PICK_CODE)
            } else {
                // Request permission
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_CODE)
            }
        } else {
            // For OS versions below Android 13, the previous approach works
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, IMAGE_PICK_CODE)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, open the gallery
                    openGallery()
                } else {
                    // Permission was denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
