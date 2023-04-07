package com.sergio.gymlog.ui.main.training.editor


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.domain.training.GetTrainingByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingEditorViewModel @Inject constructor(
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingEditorUiState())
    val uiState = _uiState.asStateFlow()

    fun loadTrainingData(idTraining : String){

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    loading = false,
                    loaded = true,
                    training = getTrainingByIdUseCase(idTraining)
                )
            }
        }

    }

    fun removeExercise(position: Int) {

        //TODO por aqui hay un fallo que hace que pete.
        viewModelScope.launch {
            _uiState.update { currentState ->

                val training = currentState.training!!
                val newExerciseList = training.exercises.minus(training.exercises[position])

                currentState.copy(
                    removedExercisePosition = position,
                    training = Training(training.id, training.name, training.description, newExerciseList)

                )

            }
        }

    }

    fun resetValues(){

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(

                    loaded = false,
                    removedExercisePosition = -1

                )
            }
        }

    }

}