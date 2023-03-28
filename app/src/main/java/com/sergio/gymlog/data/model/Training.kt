package com.sergio.gymlog.data.model

import java.util.Date


data class Training(

    val name : String = "",
    val description : String? = null,
    val date : Date? = null,
    val exercises : List<Exercises> = emptyList()

)
