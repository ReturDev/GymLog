package com.sergio.gymlog.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestoreException
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.data.authentication.FirebaseAuthenticationService
import com.sergio.gymlog.data.firestore.CloudFirestoreService
import com.sergio.gymlog.data.model.FirebaseResource
import com.sergio.gymlog.data.model.Training
import com.sergio.gymlog.util.extension.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService,
    private val firebaseAuthenticationService: FirebaseAuthenticationService
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState get() = _uiState.asStateFlow()

    suspend fun manageUserData(){

        val user = firebaseAuthenticationService.getUserData()

        if (cloudFirestoreService.existUser(user.id)){

            getUserData(user.id)

        }else{

            createUserData(user)

        }

    }

    private suspend fun createUserData(user: User){

        viewModelScope.launch {

            when(val resource = cloudFirestoreService.createNewUser(user)){
                is FirebaseResource.Failure ->{

                    _uiState.update { currentState ->

                        currentState.copy(

                            errorResource = (resource.exception as FirebaseFirestoreException).getErrorMessage()

                        )

                    }


                }
                is FirebaseResource.Success -> {

                    _uiState.update { currentState ->

                        currentState.copy(

                            loading = false

                        )

                    }

                }
            }

        }

    }

    private suspend fun getUserData(uid : String){

        viewModelScope.launch {

            when(val resource = cloudFirestoreService.getUserInfo(uid)){
                is FirebaseResource.Failure ->{

                    _uiState.update { currentState ->

                        currentState.copy(

                            errorResource = (resource.exception as FirebaseFirestoreException).getErrorMessage()

                        )

                    }


                }
                is FirebaseResource.Success -> {

                    _uiState.update { currentState ->

                        currentState.copy(

                            loading = false

                        )

                    }
                }
            }
        }
    }

    fun logout(){

        firebaseAuthenticationService.logout()

    }

}