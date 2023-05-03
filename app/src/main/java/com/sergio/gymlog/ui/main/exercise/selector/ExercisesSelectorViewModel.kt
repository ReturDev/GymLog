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
    private val exercisesSelected = mutableListOf<ExerciseItem>()

    private lateinit var exercisesExcluded : Array<String>


    fun loadExercises(exercisesExcluded: Array<String>){

        this.exercisesExcluded = exercisesExcluded

        viewModelScope.launch {


            allExerciseItems = getExercisesAsExerciseItemsUseCase(exercisesExcluded)

            _uiState.update { currentState ->

                currentState.copy(

                    exercises = allExerciseItems,
                    refresh = true,
                    exercisesSelectedQuantity = 0

                )

            }
        }

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

    fun exerciseStatusChanged(){
        _uiState.update {currentState ->
            currentState.copy(
                refresh = false,
                exerciseChangedPosition = -1,
                idExercisesToAdd = emptyList()
            )
        }
    }

    fun filter(text : String){

        viewModelScope.launch {

            _uiState.update {currentState ->

                currentState.copy(
                    refresh = true,
                    exercises = filterExercisesUseCase(name = text, exercisesExcluded = exercisesExcluded)
                )

            }

        }

    }

    fun addSelectedExercises(){

        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(

                    idExercisesToAdd = exercisesSelected.map { ex -> ex.exercise.id }

                )
            }

        }

    }



}