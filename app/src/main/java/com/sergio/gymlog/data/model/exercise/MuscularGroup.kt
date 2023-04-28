package com.sergio.gymlog.data.model.exercise

import com.sergio.gymlog.R

enum class MuscularGroup(val stringResource : Int, val iconResource : Int) {

    BACK(R.string.back_muscle, R.drawable.ic_checked_item),
    CHEST(R.string.chest_muscle, 0),
    SHOULDERS(R.string.shoulders_muscle, 0),
    LEGS(R.string.legs_muscle, 0),
    NONE(-1, 0);

}
