package com.sergio.gymlog.domain.exercise

import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.repository.user.ExercisesRepository
import javax.inject.Inject

class SaveNewUserExerciseUseCase @Inject constructor(
    private val applicationData: ApplicationData,
    private val exercisesRepository: ExercisesRepository
) {

    suspend operator fun invoke(newExercise : Exercises.UserExercise){

        newExercise.id = exercisesRepository.generateUserExerciseRandomId(applicationData.userInfo.id)

        exercisesRepository.createUserExercise(applicationData.userInfo.id, newExercise)

        applicationData.userExercises.add(newExercise)

    }

}