package com.sergio.gymlog.ui.access


data class AccessUiState(

    val loading : Boolean = false,
    val loaded : Boolean = false,
    val errorResource : Int? = null,

)
