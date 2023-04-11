package com.sergio.gymlog.ui.main

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.sergio.gymlog.domain.user.ManageUserInfoUseCase
import com.sergio.gymlog.util.extension.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val manageUserInfoUseCase: ManageUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState get() = _uiState.asStateFlow()

    suspend fun manageUserData(){

        try {

            manageUserInfoUseCase()

            _uiState.update { currentState ->

                currentState.copy(

                    loading = false

                )

            }

        }catch (fbe : FirebaseFirestoreException){

            _uiState.update { currentState ->

                currentState.copy(

                    errorResource = fbe.getErrorMessage()

                )

            }

        }

    }
}