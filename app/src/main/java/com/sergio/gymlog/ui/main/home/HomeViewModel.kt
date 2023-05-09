package com.sergio.gymlog.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.training.TrainingOfTrainingLog
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.domain.record.CreateTrainingLogUseCase
import com.sergio.gymlog.domain.training.daily.GetDailyTrainingUseCase
import com.sergio.gymlog.domain.training.daily.RemoveDailyTrainingUseCase
import com.sergio.gymlog.domain.training.daily.SetDailyTrainingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyTrainingUseCase: GetDailyTrainingUseCase,
    private val removeDailyTrainingUseCase: RemoveDailyTrainingUseCase,
    private val setDailyTrainingUseCase: SetDailyTrainingUseCase,
    private val createTrainingLogUseCase: CreateTrainingLogUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()

    fun load(){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    refresh = true,
                    dailyTraining = getDailyTrainingUseCase()
                )
            }
        }
    }

    fun loadDailyTraining(dailyTraining: UserInfo.DailyTraining?){

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    refresh = true,
                    dailyTraining = dailyTraining
                )
            }
        }

    }

    fun setDailyTraining(){

        viewModelScope.launch {

            val currentDailyTraining = _uiState.value.dailyTraining
            if (getDailyTrainingUseCase() != currentDailyTraining){

                if (currentDailyTraining == null){
                    removeDailyTrainingUseCase()
                }else{
                    setDailyTrainingUseCase(currentDailyTraining)
                }

            }

        }

    }

    fun removeDailyTraining(){
        viewModelScope.launch {

            _uiState.update { currentState->

                removeDailyTrainingUseCase()

                currentState.copy(
                    refresh = true,
                    dailyTraining = null
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

    fun complete(trainingOfTrainingLog: TrainingOfTrainingLog) {

        viewModelScope.launch {

            createTrainingLogUseCase(trainingOfTrainingLog)
            removeDailyTrainingUseCase()

            _uiState.update { currentState ->
                currentState.copy(
                    refresh = true,
                    dailyTraining = null
                )
            }

        }

    }
    fun cancel(){
        viewModelScope.launch {

            removeDailyTrainingUseCase()

            _uiState.update { currentState ->
                currentState.copy(
                    refresh = true,
                    dailyTraining = null
                )
            }

        }
    }


}