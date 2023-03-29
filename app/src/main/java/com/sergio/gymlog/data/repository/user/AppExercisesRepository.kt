package com.sergio.gymlog.data.repository.user

import com.sergio.gymlog.data.model.ApplicationData
import com.sergio.gymlog.data.service.firestore.CloudFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppExercisesRepository @Inject constructor(
    private val cloudFirestoreService: CloudFirestoreService,
    private val applicationData: ApplicationData
){
    private suspend fun getExercises(){

        applicationData.providedExercise.addAll(cloudFirestoreService.getProvidedExercises())

    }


}