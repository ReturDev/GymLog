package com.sergio.gymlog.ui.main.exercise.selector

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.MuscularGroup

interface FilterExercisesListener {

    var filterNumbers : Int

    fun filter(filterNumbers: Int, userExercises : Boolean, equipments : List<Equipment>, muscularGroups : List<MuscularGroup>){
        this.filterNumbers = filterNumbers
    }

}
