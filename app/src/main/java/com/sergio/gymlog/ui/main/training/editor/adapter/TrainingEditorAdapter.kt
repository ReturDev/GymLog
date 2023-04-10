package com.sergio.gymlog.ui.main.training.editor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.databinding.TrainingEditorExerciseItemBinding

class TrainingEditorAdapter(
    var trainingExercises : List<Exercises.TrainingExercise>,
    private val onRemoveExercise : (Int) -> Unit,
    private val onDeleteExerciseSet : (Int, Int) -> Unit
) : RecyclerView.Adapter<TrainingEditorAdapter.TrainingEditorHolder>() {

    lateinit var nestedTrainingEditorAdapter : NestedTrainingEditorAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingEditorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false)
        return TrainingEditorHolder(view)
    }

    override fun getItemCount() = trainingExercises.size

    override fun onBindViewHolder(holder: TrainingEditorHolder, position: Int) {

        holder.bind(trainingExercises[position], onRemoveExercise, onDeleteExerciseSet)

    }

    inner class TrainingEditorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingEditorExerciseItemBinding.bind(view)


        fun bind(trainingExercise: Exercises.TrainingExercise, onRemoveExercise: (Int) -> Unit, onExerciseSetDeleteClick : (Int, Int) -> Unit){

            nestedTrainingEditorAdapter = NestedTrainingEditorAdapter(trainingExercise.sets.toMutableList(), onDeleteClick = {exerciseSetPosition -> onExerciseSetDeleteClick(layoutPosition, exerciseSetPosition)})

            binding.ivTrainingEditorExImage.setImageURI(trainingExercise.image.toUri())
            binding.tvTrainingEditorExName.text = trainingExercise.name
            binding.tvTrainingEditorExEquipment.text = trainingExercise.equipment.toString()
            binding.tvTrainingEditorExMuscularGroup.text = trainingExercise.muscularGroup.toString()

            setListeners(onRemoveExercise)

        }

        private fun setListeners(onRemoveExercise: (Int) -> Unit) {

            binding.ivTrainingEditorExRemove.setOnClickListener { onRemoveExercise(layoutPosition) }

        }

    }


}