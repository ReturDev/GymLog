package com.sergio.gymlog.domain.exercise

import com.sergio.gymlog.data.model.exercise.Exercises
import javax.inject.Inject

class GetExerciseByIdUseCase @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase
) {

    suspend operator fun invoke(id : String) : Exercises{

        val allExercises = getAllExercisesUseCase()

        return allExercises.first { e -> e.id == id }

    }

}