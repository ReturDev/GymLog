package com.sergio.gymlog.data.model.temporal

import com.sergio.gymlog.data.model.exercise.Exercises
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class CreatingExercise(
    var value : Exercises.UserExercise?
){
    @Inject
    constructor() : this(null)
}
