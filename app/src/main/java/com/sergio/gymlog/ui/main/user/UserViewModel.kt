package com.sergio.gymlog.ui.main.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.access.LoginRepository
import com.sergio.gymlog.domain.user.GetUserInfoUseCase
import com.sergio.gymlog.domain.user.LogOutUseCase
import com.sergio.gymlog.domain.user.ModifyUserInfoFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val modifyUserInfoFieldUseCase: ModifyUserInfoFieldUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()

    fun loadUserData(){
        viewModelScope.launch {
            _uiState.update { currentState ->

                currentState.copy(
                    userInfoLoaded = true,
                    userInfo = getUserInfoUseCase()
                )

            }
        }
    }

    fun changeUserInfo(fieldName : String, value : String){

        viewModelScope.launch {

            modifyUserInfoFieldUseCase(fieldName,value)

            _uiState.update { currentState ->

                currentState.copy(
                    userInfoLoaded = true,
                    userInfo = getUserInfoUseCase()
                )

            }

        }

    }

    fun uiUpdated(){

        _uiState.update {currentState ->

            currentState.copy(
                userInfoLoaded = false
            )

        }

    }

    fun logOut(){

        logOutUseCase()

    }

}