package com.sergio.gymlog.domain.exercise.sets

import com.sergio.gymlog.data.model.repository.ApplicationData
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import javax.inject.Inject

class GetUserSetsPreferencesUseCase @Inject constructor(
    private val applicationData: ApplicationData
) {

    operator fun invoke() : List<TrainingExerciseSet>{

        val sets = mutableListOf<TrainingExerciseSet>()

        for (i in 1..applicationData.userInfo.sets){

            val set = TrainingExerciseSet(
                repetitions = applicationData.userInfo.repetitions,
            )

            sets.add(set)
        }

        return sets

    }

}