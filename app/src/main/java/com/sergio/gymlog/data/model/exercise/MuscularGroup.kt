package com.sergio.gymlog.data.model.exercise

import com.sergio.gymlog.R

enum class MuscularGroup(val stringResource : Int, val iconResource : Int) {

    BACK(R.string.back_muscle, R.drawable.ic_back),
    CHEST(R.string.chest_muscle, R.drawable.ic_chest),
    SHOULDERS(R.string.shoulders_muscle, R.drawable.ic_shoulders),
    LEGS(R.string.legs_muscle, R.drawable.ic_leg),
    ARMS(R.string.arms, R.drawable.ic_arms),
    NONE(-1, 0);

}
