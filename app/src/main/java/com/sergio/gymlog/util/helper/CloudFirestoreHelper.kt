package com.sergio.gymlog.util.helper


import android.util.Log
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CloudFirestoreHelper @Inject constructor() {

    fun isOnTimeDailyTraining(trainingDate : Date) : Boolean{

        Log.e("FechaBD" , trainingDate.toString() )
        Log.e("FechaActual", Date().toString())
        Log.e("FechaDIF", Date().after(trainingDate).toString())
       return Date().after(trainingDate)

    }



}