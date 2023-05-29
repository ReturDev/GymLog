package com.sergio.gymlog.ui.access

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.sergio.gymlog.data.model.remote.FirebaseResource
import com.sergio.gymlog.data.repository.access.LoginRepository
import com.sergio.gymlog.data.repository.access.SignUpRepository
import com.sergio.gymlog.util.helper.LoginAndSignUpHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val signUpRepository: SignUpRepository,
    private val loginAndSignUpHelper : LoginAndSignUpHelper
) : ViewModel(){

    private val _uiState : MutableStateFlow<AccessUiState> = MutableStateFlow(AccessUiState())
    val uiState get() = _uiState.asStateFlow()

    fun loginWithEmailAndPassword(email : String, password : String){

        login { loginRepository.loginWithEmailAndPassword(email, password) }

    }

    fun loginWithGoogle(task : Task<GoogleSignInAccount>){

        login { loginRepository.loginWithGoogleAccount(task) }

    }

    private fun login(loginMethod : suspend () -> FirebaseResource<Any>) {

        viewModelScope.launch {

            loginAndSignUpHelper.access(loginMethod, _uiState)

        }
    }

    fun signUpWithEmailAndPassword(email : String, password : String){

        signUp { signUpRepository.signUpWithEmailAndPassword(email, password) }

    }

    private fun signUp( signUpMethod : suspend () -> FirebaseResource<Any>){

        viewModelScope.launch {

            loginAndSignUpHelper.access(signUpMethod, _uiState)

        }

    }

    fun errorMessageShown(){

        loginAndSignUpHelper.errorMessageShown(_uiState)

    }

}