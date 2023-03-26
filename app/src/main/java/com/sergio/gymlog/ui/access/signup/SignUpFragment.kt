package com.sergio.gymlog.ui.access.signup

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentSignUpBinding
import com.sergio.gymlog.ui.access.AccessViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val accesViewModel : AccessViewModel by activityViewModels()
    private lateinit var binding : FragmentSignUpBinding

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

    }

    private fun setListeners() {

        binding.tvLogin.setOnClickListener {

            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

        }

        binding.btnSignUp.setOnClickListener {

            val userText = binding.etEmailLogin.text.toString()
            val passwordText = binding.etPasswordLogin.text.toString()

            accesViewModel.signUpWithEmailAndPassword(userText, passwordText)

        }

        binding.btnSignUpGoogle.setOnClickListener {
            signUpWithGoogle()
        }

    }

    private fun signUpWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            accesViewModel.signUpWithGoogle(task)

        }

    }

}