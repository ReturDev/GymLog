package com.sergio.gymlog.ui.main.exercise.creator

import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.MuscularGroup

interface ChangeDataListener {

    fun changeEquipment(equipment: Equipment)

    fun changeMuscularGroup(muscularGroup: MuscularGroup)

}