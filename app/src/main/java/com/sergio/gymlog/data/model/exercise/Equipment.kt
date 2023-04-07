package com.sergio.gymlog.data.model.exercise

import com.sergio.gymlog.R

enum class Equipment(val stringResource : Int) {
    BARBELL(R.string.barbell),
    DUMBBELLS(R.string.dumbbells),
    EZ_BAR(R.string.ez_bar),
    CABLE(R.string.cable),
    BODY_WEIGHT(R.string.body_weight),
    NONE(-1)
}
