package com.sergio.gymlog.data.model.exercise

import com.sergio.gymlog.R

enum class Equipment(val stringResource : Int, val iconResource : Int) {
    BARBELL(R.string.barbell, R.drawable.ic_checked_item),
    DUMBBELLS(R.string.dumbbells, 0),
    EZ_BAR(R.string.ez_bar, 0),
    CABLE(R.string.cable, 0),
    BODY_WEIGHT(R.string.body_weight, 0),
    NONE(-1, 0)
}
