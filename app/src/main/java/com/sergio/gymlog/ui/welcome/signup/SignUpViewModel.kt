package com.sergio.gymlog.ui.welcome.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthException
import com.sergio.gymlog.data.authentication.FirebaseAuthenticationRepository
import com.sergio.gymlog.data.model.FirebaseAuthResource
import com.sergio.gymlog.util.extension.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val firebaseRepository: FirebaseAuthenticationRepository) : ViewModel(){

    private val _uiState : MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState get() = _uiState.asStateFlow()

    fun signUpWithEmailAndPassword(email : String, password : String){

        signUp { firebaseRepository.signUpWithEmailAndPassword(email, password) }

    }

    fun signUpWithGoogle(task : Task<GoogleSignInAccount>){

        signUp { firebaseRepository.loginWithGoogleAccount(task) }

    }

    private fun signUp( loginMethod : suspend () -> FirebaseAuthResource<Any>){

        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(

                    loading = true

                )

            }

            val resource = loginMethod()

            if (resource is FirebaseAuthResource.Failure){

                resource.exception as FirebaseAuthException
                userLoginError(resource.exception.getErrorMessage())

            }

        }


    }


    private fun userLoginError(errorResource : Int){

        _uiState.update { currentState ->

            currentState.copy(

                loading = false,
                errorResource = errorResource

            )

        }

    }

    fun errorMessageShown(){

        _uiState.update { currentState ->

            currentState.copy(

                errorResource = null

            )

        }

    }

}