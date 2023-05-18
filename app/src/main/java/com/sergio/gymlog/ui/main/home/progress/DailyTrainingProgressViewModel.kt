package com.sergio.gymlog.ui.main.home.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyTrainingProgressViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(DailyTrainingProgressUiState())
    val uiState = _uiState.asStateFlow()

    private val exercisesSelected = mutableListOf<ExerciseItem>()

    fun setExercises(exercises : List<Exercises>){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    loaded = true,
                    exercises = exerciseItemsList(exercises)
                )
            }
        }
    }


    private fun exerciseItemsList(exercises : List<Exercises>) : List<ExerciseItem> {

        val exerciseItemsList = mutableListOf<ExerciseItem>()

        for (exercise in exercises){

            val selected = exercisesSelected.any { exerciseItem -> exerciseItem.exercise == exercise }

            exerciseItemsList.add(ExerciseItem(exercise, selected))
        }

        return exerciseItemsList.toList()

    }

    fun selectExercise(position : Int){
        val exercise = _uiState.value.exercises[position]
        exercise.selected = true

        exercisesSelected.add(exercise)

        changeStatusExercise(position, true)


    }

    fun deselectExercise(position : Int){

        val exercise = _uiState.value.exercises[position]
        exercise.selected = false

        exercisesSelected.remove(exercise)

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

    fun saveExercisesSelected(){

        viewModelScope.launch {
            _uiState.update { currentState ->

                currentState.copy(
                    exercisesToAdd = exercisesSelected.map { exerciseItem -> exerciseItem.exercise as Exercises.TrainingExercise }
                )

            }
        }


    }

    fun resetStateValues(){
        _uiState.update {currentState ->
            currentState.copy(
                loaded = false,
                exerciseChangedPosition = -1,
                exercisesToAdd = emptyList()
            )
        }
    }


}