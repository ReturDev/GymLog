package com.sergio.gymlog.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import com.sergio.gymlog.util.helper.CloudFirestoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val userDataRepository: UserDataRepository,
    private val cloudFirestoreHelper: CloudFirestoreHelper,

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()

//    init {
//        observeUserDataChanges()
//    }
//    private fun observeUserDataChanges(){
//
//        viewModelScope.launch {
//
//            userDataRepository.userDataState.collect{userDataState ->
//
//                if (userDataState.userInfo != null){
//
//                    _uiState.update { currentState ->
//
//                        currentState.copy(
//                            dailyTraining = userDataState.userInfo.dailyTraining,
//                            refresh = true
//                        )
//                    }
//                }
//
//            }
//        }
//    }
//
//    fun removeDailyTraining(){
//
//        try {
//
//            viewModelScope.launch {
//
//                userDataRepository.removeDailyTraining()
//
//                _uiState.update { currentState ->
//
//                    currentState.copy(
//
//                        dailyTraining = null,
//                        refresh = true
//
//                    )
//                }
//
//            }
//
//        }catch (e : Exception){
//
//        }
//
//    }
//
//    fun addDailyTraining(){
//
//        try {
//
//            viewModelScope.launch {
//
//                userDataRepository.setDailyTraining(UserInfo.DailyTraining(Date()))
//
//                _uiState.update { currentState ->
//
//                    currentState.copy(
//
//                        dailyTraining = userDataRepository.userDataState.value.userInfo!!.dailyTraining,
//                        refresh = true
//
//                    )
//                }
//
//            }
//
//        }catch (e : Exception){
//
//        }
//
//    }
//
//    fun refreshed(){
//
//        _uiState.update { currentState ->
//
//            currentState.copy(
//                refresh = true
//            )
//        }
//
//    }

}