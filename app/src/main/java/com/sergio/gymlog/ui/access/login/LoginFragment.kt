package com.sergio.gymlog.ui.access.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentLoginBinding
import com.sergio.gymlog.ui.access.AccessActivity
import com.sergio.gymlog.ui.access.AccessViewModel
import com.sergio.gymlog.util.extension.buttonActivationOnTextChanged
import com.sergio.gymlog.util.helper.LoginAndSignUpHelper
import dagger.hilt.android.AndroidEntryPoint
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
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

    }

    private fun setListeners() {

        binding.btnLogin.setOnClickListener {

            val userText = binding.etEmailLogin.text.toString()
            val passwordText = binding.etPasswordLogin.text.toString()

            accessViewModel.loginWithEmailAndPassword(userText, passwordText)


        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLoginGoogle.setOnClickListener {
            (requireActivity() as AccessActivity).googleAccess()
        }

        binding.etEmailLogin.buttonActivationOnTextChanged(binding.btnLogin, binding.etPasswordLogin)
        binding.etPasswordLogin.buttonActivationOnTextChanged(binding.btnLogin, binding.etEmailLogin)
    }





}