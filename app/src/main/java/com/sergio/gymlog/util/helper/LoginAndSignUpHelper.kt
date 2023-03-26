package com.sergio.gymlog.util.helper

import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.FirebaseResource
import com.sergio.gymlog.ui.access.AccessUiState
import com.sergio.gymlog.util.extension.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginAndSignUpHelper @Inject constructor(){

    suspend fun access(accessMethod : suspend () -> FirebaseResource<Any>, uiState : MutableStateFlow<AccessUiState>){

        uiState.update { currentState ->

            currentState.copy(

                loading = true

            )

        }

        val resource = accessMethod()

        if (resource is FirebaseResource.Failure){

            if (resource.exception is FirebaseAuthException){

                userLoginError(resource.exception.getErrorMessage(), uiState)

            }else if (resource.exception is FirebaseNetworkException || resource.exception is ApiException) {

                userLoginError(R.string.firebase_error_network, uiState)

            }

        }

    }

    private fun userLoginError(errorResource : Int, uiState : MutableStateFlow<AccessUiState>){

        uiState.update { currentState ->

            currentState.copy(

                loading = false,
                errorResource = errorResource

            )

        }

    }

    fun errorMessageShown(uiState : MutableStateFlow<AccessUiState>){

        uiState.update { currentState ->

            currentState.copy(

                errorResource = null

            )

        }

    }

}