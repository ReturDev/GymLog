package com.sergio.gymlog.data.model

enum class MuscularGroup {

    ESPALDA,
    PECHO,
    HOMBROS,
    PIERNAS;

    override fun toString(): String {
        return this.name.lowercase().replaceFirstChar { c -> c.uppercase() }
    }

}
