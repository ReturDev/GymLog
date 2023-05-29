package com.sergio.gymlog.ui.access.login.password

data class PasswordForgottenUiState (

    val loading : Boolean = false,
    val loaded : Boolean = false,
    val error : Boolean = false,
    val errorResource : Int = 0

)