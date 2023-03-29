package com.sergio.gymlog.data.model



sealed class Exercises{

    abstract val name : String
    abstract val image : String
    abstract val description : String
    abstract val equipment : Equipment
    abstract val muscularGroup : MuscularGroup

    data class ProvidedExercise(
        var id: String = "",
        override val name: String,
        override val image: String,
        override val description: String,
        override val equipment: Equipment,
        override val muscularGroup: MuscularGroup
    ) : Exercises()

    data class UserExercise(
        var id: String = "",
        override val name: String,
        override val image: String,
        override val description: String,
        override val equipment: Equipment,
        override val muscularGroup: MuscularGroup
    ) : Exercises()

    data class RecordTrainingExercise(
        override val name: String,
        override val image: String,
        override val description: String,
        override val equipment: Equipment,
        override val muscularGroup: MuscularGroup,
        var observations : String = "",
        val sets : List<TrainingSet>
    ) : Exercises()

    data class TrainingExercise(
        var id: String = "",
        override val name: String,
        override val image: String,
        override val description: String,
        override val equipment: Equipment,
        override val muscularGroup: MuscularGroup,
        val sets : List<TrainingSet>,
    ) : Exercises()

    inline fun <reified T : Exercises> convertTo(): T = T::class.java
        .getDeclaredConstructor(String::class.java,String::class.java,String::class.java,Equipment::class.java,MuscularGroup::class.java)
        .newInstance(this.name,this.image,this.description,this.equipment,this.muscularGroup)

}




