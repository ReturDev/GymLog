package com.sergio.gymlog.ui.main.exercise.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.temporal.CreatingExercise
import com.sergio.gymlog.domain.exercise.SaveNewUserExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseCreatorViewModel @Inject constructor(
    private val creatingExercise: CreatingExercise,
    private val saveNewUserExerciseUseCase: SaveNewUserExerciseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExerciseCreatorUiState())
    val uiState = _uiState.asStateFlow()
//
//    fun loadCreatingExerciseInfo(){
//
//        viewModelScope.launch {
//
//            _uiState.update { currentState ->
//
//                currentState.copy(
//                    creatingExerciseData = creatingExercise.value
//                )
//
//            }
//
//        }
//
//    }

    fun createNewExercise(newExercise: Exercises.UserExercise) {
        viewModelScope.launch {
            _uiState.update {currentState ->

                saveNewUserExerciseUseCase(newExercise)

                currentState.copy(
                    saved = true
                )
            }
        }
    }

}