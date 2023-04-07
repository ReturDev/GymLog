package com.sergio.gymlog.domain.exercise

import com.google.firebase.firestore.DocumentReference
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.repository.user.ExercisesRepository
import javax.inject.Inject

class GetExerciseReferenceUseCase @Inject constructor(
    private val getUserExercisesUseCase: GetUserExercisesUseCase,
    private val exercisesRepository: ExercisesRepository,
    private val applicationData: ApplicationData
) {

    suspend operator fun invoke(exercise: Exercises) : DocumentReference{

        val userExercises = getUserExercisesUseCase()

        val reference : DocumentReference

        if (userExercises.contains(exercise)){

            reference = exercisesRepository.getUserExerciseReference(exercise.id, applicationData.userInfo.id)

        }else{

            reference = exercisesRepository.getExerciseReference(exercise.id)

        }

        return reference

    }

}