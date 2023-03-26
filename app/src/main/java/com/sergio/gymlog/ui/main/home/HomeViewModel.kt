package com.sergio.gymlog.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.repository.firestore.CloudFirestoreService
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.data.repository.user.UserDataRepository
import com.sergio.gymlog.util.helper.CloudFirestoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val userDataRepository: UserDataRepository,
    private val cloudFirestoreHelper: CloudFirestoreHelper,

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        observeUserDataChanges()
    }
    private fun observeUserDataChanges(){

        viewModelScope.launch {

            userDataRepository.userDataState.collect{userDataState ->

                if (userDataState.userData != null){

                    _uiState.update { currentState ->

                        currentState.copy(
                            dailyTraining = userDataState.userData.dailyTraining,
                            refresh = true
                        )
                    }
                }

            }
        }
    }

    fun removeDailyTraining(){

        viewModelScope.launch {

            userDataRepository.removeDailyTraining()

            _uiState.update { currentState ->

                currentState.copy(

                    dailyTraining = null,
                    refresh = true

                )
            }

        }

    }

    fun refreshed(){

        _uiState.update { currentState ->

            currentState.copy(
                refresh = true
            )
        }

    }

}