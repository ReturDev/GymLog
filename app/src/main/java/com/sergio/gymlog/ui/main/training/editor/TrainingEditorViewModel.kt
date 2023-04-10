package com.sergio.gymlog.ui.main.training.editor


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.domain.exercise.GetExerciseByIdUseCase
import com.sergio.gymlog.domain.training.CreateUserTrainingUseCase
import com.sergio.gymlog.domain.training.GetTrainingByIdUseCase
import com.sergio.gymlog.domain.training.ModifyTrainingUseCase
import com.sergio.gymlog.domain.training.sets.GetUserSetsPreferencesUseCase
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
    private val getUserSetsPreferencesUseCase: GetUserSetsPreferencesUseCase
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

                if (idExercises.isNotEmpty() && !training.exercises.map { e -> e.id }.contains(idExercises[0])){

                    loadAndAddNewExercises(idExercises, training)

                }else{

                    _uiState.update { currentState ->
                        currentState.copy(
                            loading = false,
                            loaded = true,
                            training = training
                        )
                    }

                }

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
                    training = Training(training.id, training.name, training.description, newExerciseList)

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

    fun deleteExerciseSet(exercisePosition: String, position: Int) {
        TODO("Not yet implemented")
    }

}