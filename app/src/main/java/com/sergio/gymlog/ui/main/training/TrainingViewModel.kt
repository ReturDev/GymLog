package com.sergio.gymlog.ui.main.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.domain.training.DeleteTrainingUseCase
import com.sergio.gymlog.domain.training.GetUserTrainingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val getUserTrainingsUseCase: GetUserTrainingsUseCase,
    private val deleteTrainingUseCase: DeleteTrainingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingUiState())
    val uiState = _uiState.asStateFlow()

     fun loadTrainings() {

        viewModelScope.launch {

            val trainings = getUserTrainingsUseCase()

            _uiState.update { currentState ->

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
                trainingDeletedPosition = -1
            )
        }
    }

    fun deleteTraining(trainingPos: Int) {

        viewModelScope.launch {

            _uiState.update { currentState ->

                deleteTrainingUseCase(currentState.trainings[trainingPos].id)

                currentState.copy(

                    trainingDeletedPosition = trainingPos

                )
            }
        }


    }

}