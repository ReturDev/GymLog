package com.sergio.gymlog.ui.main.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.ApplicationData
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.repository.user.ExercisesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesSelectorViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository,
    private val applicationData: ApplicationData
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExercisesSelectorUiState())
    val uiState = _uiState.asStateFlow()

    private val allExercises : MutableList<Exercises> = mutableListOf()
    private val exercisesSelected : MutableList<Exercises> = mutableListOf()

    init {
        viewModelScope.launch {
            exercisesRepository.getProvidedExercises()
            exercisesRepository.getUserExercises()
        }
    }

    fun loadExercises(exercisesSelected: Array<String>){

        val exercises = applicationData.providedExercise + applicationData.userExercises

        for (exercise in exercises){
            if (exercise is Exercises.ProvidedExercise){
                if (!exercisesSelected.contains(exercise.id)){
                    allExercises.add(exercise)
                }
            }else if (exercise is Exercises.UserExercise){
                if (!exercisesSelected.contains(exercise.id)){
                    allExercises.add(exercise)
                }
            }

        }

        _uiState.update { currentState ->

            currentState.copy(

                loaded = true

            )

        }

    }

    fun selectExercise(position : Int){
        val exercise = _uiState.value.exercises[position]
        exercisesSelected.add(exercise)

        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(

                    exercisesSelectedSize = currentState.exercisesSelectedSize+1

                )
            }

        }

    }

    fun deselectExercise(position : Int){

        val exercise = _uiState.value.exercises[position]
        exercisesSelected.remove(exercise)

        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(

                    exercisesSelectedSize = currentState.exercisesSelectedSize-1

                )
            }

        }

    }

    fun filter(){



    }

    fun addSelectedExercises(){

        val idExercises = mutableListOf<String>()

        for (exercise in exercisesSelected){
            if (exercise is Exercises.ProvidedExercise){
                idExercises.add(exercise.id)
            }else if (exercise is Exercises.UserExercise){
                idExercises.add(exercise.id)
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






}