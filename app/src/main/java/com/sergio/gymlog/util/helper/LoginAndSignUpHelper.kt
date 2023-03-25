package com.sergio.gymlog.util.helper

import com.google.firebase.auth.FirebaseAuthException
import com.sergio.gymlog.data.model.FirebaseResource
import com.sergio.gymlog.ui.welcome.UserAccesUiState
import com.sergio.gymlog.util.extension.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoginAndSignUpHelper {

    suspend fun acces(accesMethod : suspend () -> FirebaseResource<Any>, uiState : MutableStateFlow<UserAccesUiState>){

        uiState.update { currentState ->

            currentState.copy(

                loading = true

            )

        }

        val resource = accesMethod()

        if (resource is FirebaseResource.Failure){

            resource.exception as FirebaseAuthException
            userLoginError(resource.exception.getErrorMessage(), uiState)

        }

    }

    private fun userLoginError(errorResource : Int, uiState : MutableStateFlow<UserAccesUiState>){

        uiState.update { currentState ->

            currentState.copy(

                loading = false,
                errorResource = errorResource

            )

        }

    }

    fun errorMessageShown(uiState : MutableStateFlow<UserAccesUiState>){

        uiState.update { currentState ->

            currentState.copy(

                errorResource = null

            )

        }

    }

}