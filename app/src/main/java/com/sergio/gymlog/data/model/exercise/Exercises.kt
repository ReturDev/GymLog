package com.sergio.gymlog.data.model.exercise

import javax.inject.Inject


sealed class Exercises{
    abstract var id : String
    abstract var name : String
    abstract var image : String
    abstract var description : String
    abstract var equipment : Equipment
    abstract var muscularGroup : MuscularGroup

    data class ProvidedExercise @Inject constructor(
        override var id: String,
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup
    ) : Exercises(){
        constructor():this("","","","", Equipment.NONE, MuscularGroup.NONE)

    }

    data class UserExercise @Inject constructor(
        override var id: String,
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup
    ) : Exercises(){
        constructor():this("","","","", Equipment.NONE, MuscularGroup.NONE)
    }

    data class RecordTrainingExercise @Inject constructor(
        override var id: String,
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup,
        var observations : String,
        val sets : List<TrainingExerciseSet>
    ) : Exercises(){

        constructor():this("","","","", Equipment.NONE, MuscularGroup.NONE, "", emptyList())
        constructor(id : String, name : String, image: String, description: String, equipment: Equipment, muscularGroup: MuscularGroup) :
                this(id,name,image,description,equipment,muscularGroup, "", emptyList())

    }

    data class TrainingExercise @Inject constructor(
        override var id: String,
        override var name: String,
        override var image: String,
        override var description: String,
        override var equipment: Equipment,
        override var muscularGroup: MuscularGroup,
        var sets: List<TrainingExerciseSet>
    ) : Exercises(){
        constructor():this("","","","", Equipment.NONE, MuscularGroup.NONE, emptyList())
        constructor(id : String, name : String, image: String, description: String, equipment: Equipment, muscularGroup: MuscularGroup) :
                this(id,name,image,description,equipment,muscularGroup, emptyList())

    }

    inline fun <reified T : Exercises> convertTo(): T = T::class.java
        .getDeclaredConstructor(String::class.java,String::class.java,String::class.java,String::class.java,
            Equipment::class.java, MuscularGroup::class.java)
        .newInstance(this.id,this.name,this.image,this.description,this.equipment,this.muscularGroup)

}




