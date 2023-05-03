package com.sergio.gymlog.data.repository.user

import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.training.TrainingLog
import com.sergio.gymlog.data.remote.firestore.CloudFirestoreService
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService
) {

    suspend fun getTrainingLogs(userID : String): List<TrainingLog> {
        val logs  = mutableListOf<TrainingLog>()

        val data = cloudFirestoreService.getTrainingLogs(userID)?.documents

        data?.let {

            for ( d in it){

                logs.add(d.toObject()!!)

            }

        }

        return logs
    }


}