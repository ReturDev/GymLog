package com.sergio.gymlog.data.repository.user

import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.remote.firestore.CloudFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingsRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService
) {

    suspend fun getUserTrainings(userUID: String) : List<TrainingCloud>{

        val trainingsList = mutableListOf<TrainingCloud>()

        val data = cloudFirestoreService.getUserTrainings(userUID)?.documents

        data?.let {

            for ( d in it){

                trainingsList.add(d.toObject()!!)

            }

        }

        return trainingsList

    }

    suspend fun createUserTraining(userUID: String, training : TrainingCloud){

        val newTraining = training.copy(id = cloudFirestoreService.generateTrainingRandomId(userUID))

        cloudFirestoreService.createUserTraining(userUID, newTraining)

    }

    suspend fun modifyUserTraining(userUID: String, training: TrainingCloud){

        cloudFirestoreService.setUserTraining(userUID, training)

    }

    suspend fun deleteUserTraining(userUID: String, trainingID : String){
        cloudFirestoreService.deleteUserTraining(userUID, trainingID)
    }

}