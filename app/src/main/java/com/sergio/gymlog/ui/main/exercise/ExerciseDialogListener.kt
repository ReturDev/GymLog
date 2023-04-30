package com.sergio.gymlog.ui.main.exercise

import com.sergio.gymlog.data.model.exercise.Exercises

interface ExerciseDialogListener {

    fun onClickInformation(exercisePos : Int)

    fun onClickDelete(exercisePos : Int)

}