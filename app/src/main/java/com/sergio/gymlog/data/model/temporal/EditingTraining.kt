package com.sergio.gymlog.data.model.temporal

import com.sergio.gymlog.data.model.training.Training
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class EditingTraining(
    var value: Training?
){

    @Inject constructor() : this(null)

}
