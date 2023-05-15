package com.sergio.gymlog.ui.main.exercise

import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.ui.main.training.DeleteTrainingListener

interface ExerciseDialogListener : DeleteTrainingListener{

    fun onClickInformation(exercisePos : Int)

    override fun onClickDelete(position : Int)

}