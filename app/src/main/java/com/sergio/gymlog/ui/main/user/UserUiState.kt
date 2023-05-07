package com.sergio.gymlog.ui.main.user

import com.sergio.gymlog.data.model.user.UserInfo

data class UserUiState (

    val userInfoLoaded : Boolean = false,
    val userInfo : UserInfo? = null

)