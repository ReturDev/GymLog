package com.sergio.gymlog.ui.main.training.dailytraining

import androidx.lifecycle.ViewModel
import com.sergio.gymlog.data.repository.user.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ModifyDailyTrainingViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {



}