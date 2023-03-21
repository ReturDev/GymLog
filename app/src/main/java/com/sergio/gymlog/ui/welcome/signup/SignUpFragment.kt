package com.sergio.gymlog.ui.welcome.signup

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentLoginBinding
import com.sergio.gymlog.databinding.FragmentSignUpBinding
import com.sergio.gymlog.util.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding : FragmentSignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()

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
        setCollector()

    }

    private fun setListeners() {

        binding.tvLogin.setOnClickListener {

            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

        }

        binding.btnSignUp.setOnClickListener {

            val userText = binding.etEmailLogin.text.toString()
            val passwordText = binding.etPasswordLogin.text.toString()

            signUpViewModel.signUpWithEmailAndPassword(userText, passwordText)

        }

        binding.btnSignUpGoogle.setOnClickListener {
            signUpWithGoogle()
        }

    }



    private fun setCollector(){

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED){

                signUpViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.pbSignUpLoading.visibility = View.VISIBLE

                    }else{

                        binding.pbSignUpLoading.visibility = View.GONE

                    }

                    if (currentState.errorResource != null){

                        requireActivity().toast(getString(currentState.errorResource))
                        signUpViewModel.errorMessageShown()

                    }

                }

            }

        }

    }

    private fun signUpWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            signUpViewModel.signUpWithGoogle(task)

        }

    }

}