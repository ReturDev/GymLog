package com.sergio.gymlog.ui.main.home.selector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.domain.training.GetUserTrainingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyTrainingSelectorViewModel @Inject constructor(
    private val getUserTrainingsUseCase: GetUserTrainingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyTrainingSelectorUiState())
    val uiState = _uiState.asStateFlow()

    fun loadTrainings(){

        viewModelScope.launch {
            _uiState.update {currentState ->

                currentState.copy(
                    loading = true
                )

            }

            _uiState.update { currentState ->
                currentState.copy(
                    loading = false,
                    loaded = true,
                    trainings = getUserTrainingsUseCase()
                )
            }
        }

    }

}