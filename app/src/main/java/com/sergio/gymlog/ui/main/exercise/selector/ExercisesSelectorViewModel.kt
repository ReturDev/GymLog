package com.sergio.gymlog.ui.main.exercise.selector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.domain.exercise.*
import com.sergio.gymlog.domain.exercise.filter.FilterExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesSelectorViewModel @Inject constructor(
    private val getExercisesAsExerciseItemsUseCase: GetExercisesAsExerciseItemsUseCase,
    private val filterExercisesUseCase: FilterExercisesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExercisesSelectorUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var allExerciseItems : List<ExerciseItem>
    private lateinit var filteredExercises : List<ExerciseItem>


    fun loadExercises(exercisesSelected: Array<String>){

        viewModelScope.launch {

            allExerciseItems = getExercisesAsExerciseItemsUseCase(exercisesSelected)

            _uiState.update { currentState ->

                currentState.copy(

                    loaded = true,
                    exercises = allExerciseItems,
                    refresh = true

                )

            }
        }

    }

    fun selectExercise(position : Int){
        _uiState.value.exercises[position].selected = true

        changeStatusExercise(position, true)

    }

    fun deselectExercise(position : Int){

        _uiState.value.exercises[position].selected = false

        changeStatusExercise(position, false)

    }

    private fun changeStatusExercise(position : Int, incremented : Boolean){

        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(

                    exerciseChangedPosition = position,
                    exercisesSelectedQuantity = if(incremented){
                        currentState.exercisesSelectedQuantity+1
                    } else {
                        currentState.exercisesSelectedQuantity-1
                    }

                )
            }

        }

    }

    fun exerciseStatusChanged(){
        _uiState.update {currentState ->
            currentState.copy(
                exerciseChangedPosition = -1
            )
        }
    }

    fun filter(){

        viewModelScope.launch {

            //filterExercisesUseCase("Hola")

        }

    }

    fun addSelectedExercises(){

        val idExercises = mutableListOf<String>()

        for (exerciseItem in allExerciseItems){
            if (exerciseItem.exercise is Exercises.ProvidedExercise && exerciseItem.selected){
                idExercises.add(exerciseItem.exercise.id)
            }else if (exerciseItem.exercise is Exercises.UserExercise && exerciseItem.selected){
                idExercises.add(exerciseItem.exercise.id)
            }
        }

        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(

                    idExercisesToAdd = idExercises

                )
            }

        }

    }

    fun refreshed() {
        _uiState.update {currentState ->

            currentState.copy(
                refresh = false
            )

        }
    }


}