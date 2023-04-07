package com.sergio.gymlog.ui.main.training.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.gymlog.domain.training.GetTrainingByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingDetailViewModel @Inject constructor(
 private val getTrainingByIdUseCase: GetTrainingByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun loadTraining(id : String){

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(

                    training = getTrainingByIdUseCase(id)

                )
            }
        }

    }

}