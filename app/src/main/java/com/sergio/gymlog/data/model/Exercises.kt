package com.sergio.gymlog.data.model

import javax.inject.Inject


sealed class Exercises{

    abstract var name : String
    abstract var image : String
    abstract var description : String
    abstract var equipment : Equipment
    abstract var muscularGroup : MuscularGroup

    data class ProvidedExercise @Inject constructor(
        var id: String,
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup
    ) : Exercises(){

        constructor():this("","","","",Equipment.BARRA, MuscularGroup.PECHO)

    }

    data class UserExercise @Inject constructor(
        var id: String,
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup
    ) : Exercises(){

        constructor():this("","","","",Equipment.BARRA, MuscularGroup.PECHO)

    }

    data class RecordTrainingExercise @Inject constructor(
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup,
        var observations : String,
        val sets : List<TrainingSet>
    ) : Exercises()

    data class TrainingExercise @Inject constructor(
        var id: String,
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup,
        val sets : List<TrainingSet>,
    ) : Exercises()

    inline fun <reified T : Exercises> convertTo(): T = T::class.java
        .getDeclaredConstructor(String::class.java,String::class.java,String::class.java,Equipment::class.java,MuscularGroup::class.java)
        .newInstance(this.name,this.image,this.description,this.equipment,this.muscularGroup)

}




