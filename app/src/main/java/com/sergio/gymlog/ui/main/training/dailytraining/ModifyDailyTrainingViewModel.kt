package com.sergio.gymlog.ui.main.training.dailytraining

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.user.UserDataRepository
import com.sergio.gymlog.domain.training.GetUserTrainingsUseCase
import com.sergio.gymlog.domain.training.daily.SetDailyTrainingUseCase
import com.sergio.gymlog.domain.user.GetUserInfoUseCase
import com.sergio.gymlog.ui.main.exercise.ExercisesSelectorUiState
import com.sergio.gymlog.ui.main.exercise.ExercisesSelectorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class ModifyDailyTrainingViewModel @Inject constructor(
    private val getUserTrainingsUseCase: GetUserTrainingsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val setDailyTrainingUseCase: SetDailyTrainingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModifyDailyTrainingUiState())
    val uiState = _uiState.asStateFlow()


    fun load(){
        loadTrainingSelected()
        loadTrainings()
    }

    private fun loadTrainingSelected(){

        viewModelScope.launch {

            val dailyTraining = getUserInfoUseCase().dailyTraining

            if (dailyTraining != null){

                _uiState.update { currentState ->

                    currentState.copy(
                        selectedTraining = dailyTraining,
                        trainingSelected = true
                    )

                }
            }

        }

    }
    private fun loadTrainings(){

        viewModelScope.launch{
            _uiState.update { currentState ->

                currentState.copy(
                    loading = true
                )

            }

            val trainings = getUserTrainingsUseCase()

            _uiState.update {currentState ->

                currentState.copy(
                    loading = false,
                    loaded = true,
                    trainings = trainings
                )

            }

        }

    }

    fun resetStates() {

        _uiState.update {currentState ->

            currentState.copy(
                loaded = false,
                trainingSelected = false
            )

        }

    }

    fun setTrainingSelectedAsDailyTraining(selectedTrainingPos: Int) {

        viewModelScope.launch {

            val currentDailyTraining = _uiState.value.selectedTraining
            val selectedTraining = _uiState.value.trainings[selectedTrainingPos]
            if (currentDailyTraining == null || currentDailyTraining.training != selectedTraining){

                val dailyTraining = UserInfo.DailyTraining(
                    date = Date(),
                    training = selectedTraining
                )

                setDailyTrainingUseCase(dailyTraining)
                loadTrainingSelected()

            }

        }

    }


}