package com.sergio.gymlog.ui.access.login.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.remote.FirebaseResource
import com.sergio.gymlog.data.repository.access.LoginRepository
import com.sergio.gymlog.util.extension.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.log

class PasswordForgottenViewModel(
    private val loginRepository : LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PasswordForgottenUiState())
    val uiState = _uiState.asStateFlow()

    fun sendEmail(email : String){
        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(
                    loading = true
                )

            }

            val result = loginRepository.sendPasswordRecoveryEmail(email)

            _uiState.update {currentState ->

                when(result){
                    is FirebaseResource.Failure ->

                        currentState.copy(
                            loading = false,
                            error = true,
                            errorResource = manageError(result.exception)
                        )

                    is FirebaseResource.Success ->

                        currentState.copy(
                            loading = false,
                            loaded = true
                        )

                }

            }

        }
    }

    private fun manageError (exception: Exception): Int {

        return when (exception) {
            is FirebaseAuthException -> {

                exception.getErrorMessage()

            }

            is FirebaseNetworkException, is ApiException -> {

                R.string.firebase_error_network

            }

            else -> {

                R.string.firebase_error_generic

            }
        }



    }

}