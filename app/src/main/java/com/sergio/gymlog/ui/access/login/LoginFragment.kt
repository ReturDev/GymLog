package com.sergio.gymlog.ui.access.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentLoginBinding
import com.sergio.gymlog.ui.access.AccessActivity
import com.sergio.gymlog.ui.access.AccessViewModel
import com.sergio.gymlog.util.extension.buttonActivationOnTextChanged
import com.sergio.gymlog.util.helper.LoginAndSignUpHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val accessViewModel : AccessViewModel by activityViewModels()

    @Inject
    lateinit var loginAndSignUpHelper: LoginAndSignUpHelper
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setCollector()

    }

    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                accessViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.btnLogin.isEnabled = false
                        binding.btnLoginGoogle.isEnabled = false
                        binding.tvLoginSignUp.isEnabled = false

                    }

                    if (currentState.loaded){

                        binding.btnLogin.isEnabled = true
                        binding.btnLoginGoogle.isEnabled = true
                        binding.tvLoginSignUp.isEnabled = true

                    }

                }

            }
        }

    }

    private fun setListeners() {

        binding.btnLogin.setOnClickListener {

            val userText = binding.etLoginEmail.text.toString()
            val passwordText = binding.etLoginPassword.text.toString()

            accessViewModel.loginWithEmailAndPassword(userText, passwordText)


        }

        binding.tvLoginSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLoginGoogle.setOnClickListener {
            (requireActivity() as AccessActivity).googleAccess()
        }

        binding.etLoginEmail.buttonActivationOnTextChanged(binding.btnLogin, binding.etLoginPassword)
        binding.etLoginPassword.buttonActivationOnTextChanged(binding.btnLogin, binding.etLoginEmail)
    }





}