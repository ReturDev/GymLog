package com.sergio.gymlog.ui.access.signup

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentSignUpBinding
import com.sergio.gymlog.ui.access.AccessActivity
import com.sergio.gymlog.ui.access.AccessViewModel
import com.sergio.gymlog.util.extension.buttonActivationOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val accessViewModel : AccessViewModel by activityViewModels()
    private lateinit var binding : FragmentSignUpBinding

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
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

                       changeEnabledComponents(false)

                   }
                   if (currentState.loaded){

                        changeEnabledComponents(true)

                   }

               }

            }
        }

    }

    private fun setListeners() {

        binding.tvSingUpLogin.setOnClickListener {

            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

        }

        binding.btnSignUp.setOnClickListener {

            val userText = binding.etSignUpEmail.text.toString()
            val passwordText = binding.etSignUpPassword.text.toString()

            accessViewModel.signUpWithEmailAndPassword(userText, passwordText)

        }

        binding.btnSignUpGoogle.setOnClickListener {
            signUpWithGoogle()
        }

        binding.etSignUpEmail.buttonActivationOnTextChanged(binding.btnSignUp, binding.etSignUpPassword)
        binding.etSignUpPassword.buttonActivationOnTextChanged(binding.btnSignUp, binding.etSignUpEmail)

    }

    private fun signUpWithGoogle() {
        (requireActivity() as AccessActivity).googleAccess()
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            accessViewModel.signUpWithGoogle(task)

        }

    }

    private fun changeEnabledComponents(enabled : Boolean){

        binding.btnSignUp.isEnabled = enabled
        binding.btnSignUpGoogle.isEnabled = enabled
        binding.tvSingUpLogin.isEnabled = enabled
        binding.etSignUpEmail.isEnabled = enabled
        binding.etSignUpPassword.isEnabled = enabled

    }

}