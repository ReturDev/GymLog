package com.sergio.gymlog.ui.main.training.editor


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.domain.exercise.GetExerciseByIdUseCase
import com.sergio.gymlog.domain.training.CreateUserTrainingUseCase
import com.sergio.gymlog.domain.training.GetTrainingByIdUseCase
import com.sergio.gymlog.domain.training.ModifyTrainingUseCase
import com.sergio.gymlog.domain.exercise.sets.GetUserSetsPreferencesUseCase
import com.sergio.gymlog.domain.user.GetUserInfoUseCase
import com.sergio.gymlog.util.TrainingEdit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingEditorViewModel @Inject constructor(
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase,
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    private val createUserTrainingUseCase: CreateUserTrainingUseCase,
    private val modifyTrainingUseCase: ModifyTrainingUseCase,
    private val getUserSetsPreferencesUseCase: GetUserSetsPreferencesUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingEditorUiState())
    val uiState = _uiState.asStateFlow()

    fun loadTrainingData(idTraining : String, idExercises: Array<String>){

        viewModelScope.launch {

            _uiState.update {currentState ->

                currentState.copy(
                    loading = true
                )

            }

            val training = TrainingEdit.getTraining()
            if (training != null){

                loadAndAddNewExercises(idExercises, training)

            }else{

                loadTraining(idTraining)

            }

        }

    }

    private suspend fun loadTraining(idTraining: String){

        _uiState.update { currentState ->

            currentState.copy(
                loading = false,
                loaded = true,
                training = getTrainingByIdUseCase(idTraining)
            )
        }

    }

    private suspend fun loadAndAddNewExercises(idExercises: Array<String>, training: Training) {

        val newExercises = idExercises.map { id -> getExerciseByIdUseCase(id).convertTo<Exercises.TrainingExercise>() }
        newExercises.forEach{ex -> ex.sets = getUserSetsPreferencesUseCase()}
        val newTraining = training.copy(exercises =  newExercises + training.exercises)

        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                loaded = true,
                training = newTraining
            )
        }

    }

    fun removeExercise(exercise: Exercises.TrainingExercise, position: Int) {

        viewModelScope.launch {
            _uiState.update { currentState ->

                val training = currentState.training
                val newExerciseList = training.exercises.minus(exercise)

                currentState.copy(

                    removedExercisePosition = position,
                    training = currentState.training.copy(exercises = newExerciseList)

                )

            }
        }

    }

    fun saveTrainingData(training: Training){

        viewModelScope.launch {

            if (training.id.isBlank()){

                createUserTrainingUseCase(training)

            }else{

                modifyTrainingUseCase(training)

            }

        }

    }

    fun deleteExerciseSet(exercisePosition: Int, exerciseID: String, trainingSetPosition: Int) {
        viewModelScope.launch {

            _uiState.update {currentState ->

                val exercises = currentState.training.exercises.toMutableList()
                val exerciseIndex = exercises.indexOfFirst { it.id == exerciseID }
                val sets = exercises[exerciseIndex].sets.toMutableList()
                sets.removeAt(trainingSetPosition)

                exercises[exerciseIndex] = exercises[exerciseIndex].copy(sets = sets)

                currentState.copy(

                    deletedExerciseSetPosition = trainingSetPosition,
                    changedExercisePosition = exercisePosition,
                    training = currentState.training.copy(exercises = exercises)

                )
            }

        }
    }

    fun addExerciseSet(exercisePos: Int, exerciseID: String) {
        viewModelScope.launch {

            _uiState.update { currentState ->

                val exercises = currentState.training.exercises.toMutableList()
                val exerciseIndex = exercises.indexOfFirst { it.id == exerciseID }
                val sets = exercises[exerciseIndex].sets.toMutableList()
                val set = TrainingExerciseSet(
                    repetitions = getUserInfoUseCase().repetitions
                )
                sets.add(set)

                exercises[exerciseIndex] = exercises[exerciseIndex].copy(sets = sets)

                currentState.copy(

                    exerciseSetInserted = true,
                    newExerciseSet = set ,
                    changedExercisePosition = exercisePos,
                    training = currentState.training.copy(exercises = exercises)

                )

            }
        }
    }

    fun resetValues(){

        _uiState.update { currentState ->
            currentState.copy(

                loaded = false,
                removedExercisePosition = -1,
                deletedExerciseSetPosition = -1,
                exerciseSetInserted = false

            )
        }

    }

    fun onExerciseSetValueChanged(
        exerciseID: String,
        exerciseSetPos: Int,
        exerciseSet: TrainingExerciseSet
    ) {

        viewModelScope.launch {
            _uiState.update { currentState ->

                val exercises = currentState.training.exercises.toMutableList()
                val exerciseIndex = exercises.indexOfFirst { it.id == exerciseID }
                val sets = exercises[exerciseIndex].sets.toMutableList()
                sets[exerciseSetPos] = exerciseSet

                exercises[exerciseIndex] = exercises[exerciseIndex].copy(sets = sets)

                currentState.copy(

                    training = currentState.training.copy(exercises = exercises)

                )

            }
        }


    }

}