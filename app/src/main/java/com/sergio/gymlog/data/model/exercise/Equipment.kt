package com.sergio.gymlog.data.model.exercise

import com.sergio.gymlog.R

enum class Equipment(val stringResource : Int, val iconResource : Int) {
    BARBELL(R.string.barbell, R.drawable.ic_barbell),
    DUMBBELLS(R.string.dumbbells, R.drawable.ic_dumbbell),
    EZ_BAR(R.string.ez_bar, R.drawable.ic_ez_bar),
    CABLE(R.string.cable, R.drawable.ic_cable),
    MACHINE(R.string.machine, R.drawable.ic_machine),
    BODY_WEIGHT(R.string.body_weight, R.drawable.ic_bodyweight),
    NONE(-1, 0)
}
