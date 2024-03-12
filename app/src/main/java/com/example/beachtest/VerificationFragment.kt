package com.example.beachtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beachtest.databinding.FragmentResetPasswordBinding
import com.example.beachtest.databinding.FragmentVerificationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VerificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class    VerificationFragment : Fragment() {
    private lateinit var binding: FragmentVerificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerificationBinding.inflate(inflater, container, false)

        binding.codeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_verificationFragment_to_resetPasswordFragment)
        }

        binding.backToLogInButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_verificationFragment_to_signInFragment)
        }

        return binding.root
    }


}