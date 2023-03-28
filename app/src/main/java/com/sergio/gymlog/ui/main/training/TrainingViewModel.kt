package com.sergio.gymlog.ui.main.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.repository.user.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {


    fun changeOption(){

        viewModelScope.launch {
            userDataRepository.removeDailyTraining()
        }

    }

}