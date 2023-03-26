package com.sergio.gymlog.ui.main.home

import android.service.autofill.UserData
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.firestore.CloudFirestoreService
import com.sergio.gymlog.data.model.User
import com.sergio.gymlog.util.helper.CloudFirestoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val cloudFirestoreService: CloudFirestoreService,
    private val cloudFirestoreHelper: CloudFirestoreHelper,
    userData: User

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()
    private val userDataFlow = MutableStateFlow(userData)

    init {
        checkUserDataChange()
    }
    private fun checkUserDataChange(){

       viewModelScope.launch {

           userDataFlow.collect{

               if (it.id != ""){
                   checkDailyTraining(it)
               }

           }

       }

    }

    private fun checkDailyTraining(userData : User) {
        viewModelScope.launch {

            _uiState.update { currentState ->

                Log.e("FechaBD" , userData.toString() )

                currentState.copy(

                    dailyTraining = userData.dailyTraining?.date?.let { d -> cloudFirestoreHelper.isOnTimeDailyTraining(d)} ?: false

                )

            }

        }
    }
}