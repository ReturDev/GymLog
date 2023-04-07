package com.sergio.gymlog.data.model.exercise

import com.sergio.gymlog.R

enum class MuscularGroup(val stringResource : Int) {

    BACK(R.string.back_muscle),
    CHEST(R.string.chest_muscle),
    SHOULDERS(R.string.shoulders_muscle),
    LEGS(R.string.legs_muscle),
    NONE(-1);

}
