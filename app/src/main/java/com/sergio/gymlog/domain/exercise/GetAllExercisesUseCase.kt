package com.sergio.gymlog.domain.exercise


import com.sergio.gymlog.data.model.exercise.Exercises
import javax.inject.Inject

class GetAllExercisesUseCase @Inject constructor(
    private val getProvidedExercisesUserCase: GetProvidedExercisesUserCase,
    private val getUserExercisesUseCase: GetUserExercisesUseCase
) {

    suspend operator fun invoke() : List<Exercises>{

        return getProvidedExercisesUserCase() + getUserExercisesUseCase()

    }

}