package com.sergio.gymlog.domain.exercise;

import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.repository.user.ExercisesRepository
import javax.inject.Inject;

class GetUserExercisesUseCase @Inject constructor(
    private val exercisesRepository: ExercisesRepository,
    private val applicationData: ApplicationData
) {

    suspend operator fun invoke() : List<Exercises.UserExercise>{

        if (!applicationData.userExercisesConsulted){

            applicationData.userExercises.addAll(exercisesRepository.getUserExercises(applicationData.userInfo.id))

        }

        return applicationData.userExercises

    }

}
