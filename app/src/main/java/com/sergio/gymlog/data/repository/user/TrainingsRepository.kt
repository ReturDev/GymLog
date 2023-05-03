package com.sergio.gymlog.data.repository.user

import com.google.firebase.firestore.ktx.toObject
import com.sergio.gymlog.data.model.remote.firestore.TrainingCloud
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.remote.firestore.CloudFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingsRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService
) {

    suspend fun getUserTrainings(userID: String) : List<TrainingCloud>{

        val trainingsList = mutableListOf<TrainingCloud>()

        val data = cloudFirestoreService.getUserTrainings(userID)?.documents

        data?.let {

            for ( d in it){

                trainingsList.add(d.toObject()!!)

            }

        }

        return trainingsList

    }

    suspend fun generateTrainingRandomId(userID: String) : String{

        return cloudFirestoreService.generateTrainingRandomId(userID)

    }

    suspend fun createUserTraining(userID: String, training : TrainingCloud) : Training{

        cloudFirestoreService.createUserTraining(userID, training)

        return Training()

    }

    suspend fun modifyUserTraining(userID: String, training: TrainingCloud){

        cloudFirestoreService.setUserTraining(userID, training)

    }

    suspend fun deleteUserTraining(userID: String, trainingID : String){
        cloudFirestoreService.deleteUserTraining(userID, trainingID)
    }

}