package com.sergio.gymlog.ui.main.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.data.repository.user.UserDataRepository
import com.sergio.gymlog.domain.training.GetUserTrainingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val getUserTrainingsUseCase: GetUserTrainingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTrainings()
    }

    private fun loadTrainings() {

        viewModelScope.launch {

            val trainings = getUserTrainingsUseCase()

            _uiState.update { currentState ->

                currentState.copy(
                    trainings = trainings
                )

            }

        }

    }

}