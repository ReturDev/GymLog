package com.sergio.gymlog.ui.main.exercise.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.domain.exercise.DeleteUserExerciseUseCase
import com.sergio.gymlog.domain.exercise.GetUserExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserExercisesListViewModel @Inject constructor(
    private val getUserExercisesUseCase: GetUserExercisesUseCase,
    private val deleteUserExerciseUseCase: DeleteUserExerciseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserExercisesListUiState())
    val uiState = _uiState.asStateFlow()

    fun loadUserExercises(){

        viewModelScope.launch {


            _uiState.update {currentState ->

                currentState.copy(
                    loading = false
                )

            }

            _uiState.update {currentState ->

                currentState.copy(
                    userExercises = getUserExercisesUseCase(),
                    loading = false,
                    loaded = true
                )

            }
        }

    }

    fun resetStates(){
        _uiState.update { currentState->
            currentState.copy(
                loaded = false,
                exerciseDeletedPos = -1
            )
        }
    }

    fun deleteUserExercise(exercisePos: Int) {

        viewModelScope.launch {

            deleteUserExerciseUseCase(uiState.value.userExercises[exercisePos])

            _uiState.update {currentState ->

                currentState.copy(
                    exerciseDeletedPos = exercisePos
                )

            }
        }

    }


}