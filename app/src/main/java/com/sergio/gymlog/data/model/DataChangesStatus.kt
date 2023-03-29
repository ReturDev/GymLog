package com.sergio.gymlog.data.model


data class DataChangesStatus(
    val changesOnUserInfo : Boolean = false,
    val changesOnUserExercises : Boolean = false,
    val changesOnUserTrainings : Boolean = false
)
