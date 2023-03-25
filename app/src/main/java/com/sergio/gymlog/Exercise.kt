package com.sergio.gymlog

import java.net.URI

data class Exercise(

    val name : String,
    val imageDesc : URI,
    val description : String = "",
    val equipment : Equipment,
    val muscularGroup : MuscularGroup,
    val observations : String? = null,
    val sets : List<Set>

){

    data class Set(

        var repetitions : Int = 1,
        var weight : Int,
        var bodyWeight : Boolean = false

    )


}
