package com.sergio.gymlog.ui.access.login.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.DialogPasswordForgottenBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PasswordForgottenDialog : BottomSheetDialogFragment() {

    private lateinit var binding : DialogPasswordForgottenBinding

    private val passwordForgottenVM by viewModels<PasswordForgottenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogPasswordForgottenBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setListeners()
        setCollectors()


    }

    private fun setCollectors() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                passwordForgottenVM.uiState.collect{currentState ->

                    if (currentState.loading){

                        manageComponentsEnabled(false)

                    }

                    if (currentState.loaded){

                        manageComponentsEnabled(true)

                    }

                    if (currentState.error){

                        manageComponentsEnabled(true)


                    }

                }

            }
        }


    }

    private fun setListeners() {

        binding.etPasswordForgottenEmail.addTextChangedListener {
            binding.btnPasswordForgottenSend.isEnabled = it!!.isNotBlank()
        }

        binding.btnPasswordForgottenSend.setOnClickListener {

        }

    }

    private fun manageComponentsEnabled(enabled : Boolean){

        binding.pbPasswordForgottenLoading.visibility = if (enabled) View.GONE else View.VISIBLE
        binding.btnPasswordForgottenSend.isEnabled = enabled
        binding.tilPasswordForgottenEmail.isEnabled = enabled

    }

}