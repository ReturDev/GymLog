package com.sergio.gymlog.ui.main.exercise.selector

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.MuscularGroup

interface FilterExercisesListener {

    fun filter(userExercises : Boolean, equipments : Equipment, muscularGroups : MuscularGroup)
    fun resetFilters()

}
