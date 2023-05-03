package com.sergio.gymlog.ui.main.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.sergio.gymlog.domain.record.GetTrainingLogByDateUseCase
import com.sergio.gymlog.domain.record.GetTrainingLogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getTrainingLogsUseCase: GetTrainingLogsUseCase,
    private val getTrainingLogByDateUseCase :GetTrainingLogByDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadLogs()
    }

    private fun loadLogs(){

        viewModelScope.launch {

            _uiState.update { currentState ->

                currentState.copy(
                    loading = true
                )

            }

            _uiState.update {currentState ->

                currentState.copy(
                    loading = false,
                    loaded = true,
                    trainingLogs = getTrainingLogsUseCase()
                )

            }


        }


    }

    fun getLog(date : Timestamp?){

        viewModelScope.launch {

            val logs = if (date == null){

                getTrainingLogsUseCase()

            }else {

                getTrainingLogByDateUseCase(date)

            }

            _uiState.update { currentState->

                currentState.copy(
                    refresh = true,
                    trainingLogs = logs
                )

            }
        }


    }

    fun resetState (){
        _uiState.update {currentState ->

            currentState.copy(
                loaded = false,
                refresh = false
            )

        }
    }

}