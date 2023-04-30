package com.sergio.gymlog.domain.exercise

import com.google.firebase.firestore.DocumentReference
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.repository.user.ExercisesRepository
import javax.inject.Inject

class GetUserExerciseReferenceUseCase @Inject constructor(
    private val getUserExercisesUseCase: GetUserExercisesUseCase,
    private val exercisesRepository: ExercisesRepository,
    private val applicationData: ApplicationData
) {

    suspend operator fun invoke(exercise: Exercises) : DocumentReference{

        val userExercises = getUserExercisesUseCase()

        val reference = if (userExercises.map { ex -> ex.id }.any { id -> id == exercise.id }){

            exercisesRepository.getUserExerciseReference(applicationData.userInfo.id, exercise.id )

        }else{

            exercisesRepository.getExerciseReference(exercise.id)

        }

        return reference

    }

}