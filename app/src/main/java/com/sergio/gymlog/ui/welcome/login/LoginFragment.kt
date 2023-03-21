package com.sergio.gymlog.ui.welcome.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentLoginBinding
import com.sergio.gymlog.ui.main.MainActivity
import com.sergio.gymlog.util.extension.toast
import com.sergio.gymlog.util.helper.LoginAndSignUpHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()

    @Inject
    lateinit var loginAndSignUpHelper: LoginAndSignUpHelper
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setCollector()

    }

    private fun setListeners() {

        binding.btnLogin.setOnClickListener {

            val userText = binding.etEmailLogin.text.toString()
            val passwordText = binding.etPasswordLogin.text.toString()

            loginViewModel.loginWithEmailAndPassword(userText, passwordText)


        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLoginGoogle.setOnClickListener {
            signInWithGoogle()
        }

        binding.etEmailLogin.addTextChangedListener(loginAndSignUpHelper.enableButtonListener(binding.etEmailLogin,binding.etPasswordLogin,binding.btnLogin))
        binding.etPasswordLogin.addTextChangedListener(loginAndSignUpHelper.enableButtonListener(binding.etPasswordLogin,binding.etEmailLogin, binding.btnLogin))
    }



    private fun setCollector(){

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED){

                loginViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.pbLoginLoading.visibility = View.VISIBLE

                    }else{

                        binding.pbLoginLoading.visibility = View.GONE

                    }

                    if (currentState.errorResource != null){

                        requireActivity().toast(getString(currentState.errorResource))
                        loginViewModel.errorMessageShown()

                    }

                }

            }

        }

    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            loginViewModel.loginWithGoogle(task)

        }

    }

}