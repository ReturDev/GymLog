package com.sergio.gymlog.ui.main.user

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(

) : ViewModel() {


    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()



}