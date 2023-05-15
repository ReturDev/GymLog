package com.sergio.gymlog.domain.exercise

import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.remote.firestore.ReferencedExercises
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.repository.user.ExercisesRepository

import javax.inject.Inject

class DeleteUserExerciseUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val getUserExerciseReferenceUseCase: GetUserExerciseReferenceUseCase,
    private val exercisesRepository: ExercisesRepository,

){

    suspend operator fun invoke(exercise : Exercises.UserExercise){

        val exerciseReference = getUserExerciseReferenceUseCase(exercise)
        val trainingsToUpdate = mutableMapOf<String,List<ReferencedExercises>>()

        applicationData.userTrainings.forEach { training ->

            val exercisesFiltered = training.exercises.filter { ex -> ex.id != exercise.id }

            if (exercisesFiltered.size != training.exercises.size){

                trainingsToUpdate[training.id] = exercisesFiltered.map { ex ->
                    ReferencedExercises(getUserExerciseReferenceUseCase(ex), ex.sets)
                }
                val index = applicationData.userTrainings.indexOf(training)
                applicationData.userTrainings[index] = training.copy(exercises = exercisesFiltered)

            }

        }

        exercisesRepository.deleteUserExercise(applicationData.userInfo.id,exerciseReference, trainingsToUpdate)
        applicationData.userExercises.remove(exercise)

    }

}