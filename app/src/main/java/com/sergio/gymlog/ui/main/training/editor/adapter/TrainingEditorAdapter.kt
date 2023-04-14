package com.sergio.gymlog.ui.main.training.editor.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.databinding.TrainingEditorExerciseItemBinding
import com.sergio.gymlog.ui.main.training.detail.adapter.NestedTrainingDetailsAdapter
import kotlin.math.log

class TrainingEditorAdapter(
    val trainingExercises : MutableList<Exercises.TrainingExercise>,
    private val onRemoveExercise : (exercisePos : Int) -> Unit,
    private val onAddExerciseSet : (exercisePos : Int) -> Unit,
    private val onDeleteExerciseSet : (exercisePos : Int, exerciseSetPos : Int) -> Unit,
    private val onExerciseSetValueChanged : (exerciseID : String ,exerciseSetPos : Int, trainingSet : TrainingExerciseSet) -> Unit
) : RecyclerView.Adapter<TrainingEditorAdapter.TrainingEditorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingEditorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_editor_exercise_item, parent, false)
        return TrainingEditorHolder(view)
    }

    override fun getItemCount() = trainingExercises.size

    override fun onBindViewHolder(holder: TrainingEditorHolder, position: Int) {

        holder.bind(trainingExercises[position])

    }

    inner class TrainingEditorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingEditorExerciseItemBinding.bind(view)
        lateinit var adapter : NestedTrainingEditorAdapter

        fun bind(
            trainingExercise: Exercises.TrainingExercise
        ){

            binding.ivTrainingEditorExImage.setImageURI(trainingExercise.image.toUri())
            binding.tvTrainingEditorExName.text = trainingExercise.name
            binding.tvTrainingEditorExEquipment.text = trainingExercise.equipment.toString()
            binding.tvTrainingEditorExMuscularGroup.text = trainingExercise.muscularGroup.toString()

            initNestedRecycler(trainingExercise)
            setListeners(onRemoveExercise)

        }

        private fun initNestedRecycler(trainingExercise : Exercises.TrainingExercise){
            adapter = NestedTrainingEditorAdapter(
                trainingExercise.sets.toMutableList(),
                onDeleteClick = { exerciseSetPos -> onDeleteExerciseSet(layoutPosition,exerciseSetPos)},
                onExerciseSetValueChanged = { exerciseSetPos,
                                              trainingSet ->
                    onExerciseSetValueChanged(
                        trainingExercise.id,
                        exerciseSetPos, trainingSet
                    )
                }
            )
            val recyclerView = binding.rvTrainingEditorExSets
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        }


        private fun setListeners(onRemoveExercise: (Int) -> Unit) {

            binding.ivTrainingEditorExRemove.setOnClickListener { onRemoveExercise(layoutPosition) }
            binding.btnTrainingEditorExAddSet.setOnClickListener { onAddExerciseSet(layoutPosition) }

        }

        fun deleteExerciseSet(exerciseSetPos: Int){

            adapter.exerciseSets.removeAt(exerciseSetPos)
            adapter.notifyItemRemoved(exerciseSetPos)


        }

        fun addExerciseSet(exerciseSets : TrainingExerciseSet){

            adapter.exerciseSets.add(exerciseSets)

            adapter.exerciseSets.forEach {  Log.e("SETS", it.toString() )}


            adapter.notifyItemInserted(adapter.exerciseSets.size -1)

        }

    }


}