package com.sergio.gymlog.ui.main.training.editor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.databinding.ExerciseItemBinding

class TrainingEditorAdapter(
    var trainingExercises : List<Exercises.TrainingExercise>,
    private val onRemoveExercise : (Int) -> Unit
) : RecyclerView.Adapter<TrainingEditorAdapter.TrainingEditorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingEditorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false)
        return TrainingEditorHolder(view)
    }

    override fun getItemCount() = trainingExercises.size

    override fun onBindViewHolder(holder: TrainingEditorHolder, position: Int) {

        holder.bind(trainingExercises[position], onRemoveExercise)

    }

    inner class TrainingEditorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = ExerciseItemBinding.bind(view)

        fun bind(trainingExercise: Exercises.TrainingExercise, onRemoveExercise: (Int) -> Unit){

            binding.ivRemoveItem.visibility = View.VISIBLE

            binding.ivExerciseImage.setImageURI(trainingExercise.image.toUri())
            binding.tvExerciseName.text = trainingExercise.name
            binding.tvExerciseEquipment.text = trainingExercise.equipment.toString()
            binding.tvExerciseMuscularGroup.text = trainingExercise.muscularGroup.toString()

            setListeners(onRemoveExercise)

        }

        private fun setListeners(onRemoveExercise: (Int) -> Unit) {

            binding.ivRemoveItem.setOnClickListener { onRemoveExercise(layoutPosition) }

        }


    }


}