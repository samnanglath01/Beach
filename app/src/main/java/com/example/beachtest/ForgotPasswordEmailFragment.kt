package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beachtest.databinding.FragmentForgotPasswordEmailBinding
import com.example.beachtest.databinding.FragmentSignInBinding
import com.example.beachtest.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var auth: FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordEmailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ForgotPasswordEmailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentForgotPasswordEmailBinding
    //Samnang Lath
    // onCreate lifecycle callback where the FirebaseAuth instance is initialized,
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    //Samnang Lath
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForgotPasswordEmailBinding.inflate(inflater, container, false)

        // Set up the click listener for the submit button using the binding
        // and calls sendPasswordResetEmail function with the email.
        binding.verificationButton.setOnClickListener {
            val email = binding.emailEditPasswordText.text.toString().trim() //get input
            if (email.isNotEmpty()) { //check
                sendPasswordResetEmail(email)
            } else {
                binding.emailEditPasswordText.error = "Please enter your email"
            }
        }

        //Samnang Lath
        // Set up the click listener for the "Back to Login" button using the binding
        binding.backToLogInButton.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordEmailFragment_to_signInFragment)
        }

        // Return the root view of the binding
        return binding.root
    }
    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //sucess
                    Toast.makeText(context, "Reset link sent to your email", Toast.LENGTH_LONG).show()
                } else {
                    //ffailed
                    Toast.makeText(context, "Failed to send reset email", Toast.LENGTH_LONG).show()
                }
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgotPasswordEmailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgotPasswordEmailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}