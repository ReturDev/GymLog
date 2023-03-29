package com.sergio.gymlog.ui.main.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.ExerciseItem
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.repository.user.UserDataRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class ExercisesSelectorViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExercisesSelectorUiState())
    val uiState = _uiState.asStateFlow()

    private val allExerciseItems : MutableList<ExerciseItem> = mutableListOf()

//
//    init {
//        observerUserDataChanges()
//    }
//
//    private fun observerUserDataChanges() {
//
//        viewModelScope.launch {
//            userDataRepository.userDataState.collect {data ->
//
//                _uiState.update { currentState ->
//
//                    currentState.copy(
//
//                        refresh = true
//
//                    )
//
//                }
//
//            }
//
//        }
//
//    }


    private fun exercisesToExerciseItems(exercisesSelected : List<Exercises>){

        val allExercises = userDataRepository.userDataState.value.providedExercises + userDataRepository.userDataState.value.userExercises

        for (exercise in allExercises){

            allExerciseItems.add(ExerciseItem(exercise,exercisesSelected.contains(exercise)))

        }

    }


}