package com.sergio.gymlog

import android.net.Uri
import com.sergio.gymlog.data.model.Provider

data class User(

    val id : String,
    val provider : Provider,
    val name : String?,
    val email : String?,
    val verifiedEmail : Boolean,
    val photo : Uri?,
    val phoneNumber : String?

)
