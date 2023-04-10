package com.sergio.gymlog.util

import com.sergio.gymlog.data.model.training.Training

object TrainingEdit {

    private var training : Training? = null

    fun setTraining(value : Training?){
        training = value
    }

    fun getTraining() : Training?{
        return training
    }

}