package com.sergio.gymlog.domain.exercise.sets

import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.domain.user.GetUserInfoUseCase
import javax.inject.Inject

class GetUserSetsPreferencesUseCase @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {

    suspend operator fun invoke(bodyWeight : Boolean) : List<TrainingExerciseSet>{

        val userInfo = getUserInfoUseCase()

        val sets = mutableListOf<TrainingExerciseSet>()

        for (i in 1..userInfo.sets){

            val set = if (bodyWeight){

                TrainingExerciseSet(
                    repetitions = userInfo.repetitions,
                    weight = userInfo.weight,
                    bodyWeight = true
                )

            }else{

                TrainingExerciseSet(
                    repetitions = userInfo.repetitions,
                )

            }

            sets.add(set)
        }

        return sets

    }

}