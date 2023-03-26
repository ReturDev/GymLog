package com.sergio.gymlog.data.model

import com.google.firebase.firestore.FieldValue
import java.util.Date


data class Training(

    val name : String,
    val description : String? = null,
    val date : Date? = null,
    val exercises : List<Exercise>

)
