package com.sergio.gymlog.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.databinding.TrainingExerciseItemBinding
import com.sergio.gymlog.util.extension.setImageRoundedBorders

class HomeAdapter(
private val trainingExercises : MutableList<Exercises.TrainingExercise>
) : RecyclerView.Adapter<HomeAdapter.HomeHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_exercise_item, parent, false)
        return HomeHolder(view)
    }

    override fun getItemCount() = trainingExercises.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {

        holder.bind(trainingExercises[position])

    }

    fun changeExerciseList(newExercises : List<Exercises.TrainingExercise>){
        trainingExercises.clear()
        trainingExercises.addAll(newExercises)
    }

    inner class HomeHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val binding = TrainingExerciseItemBinding.bind(view)

        fun bind(trainingExercise: Exercises.TrainingExercise) {

            binding.tvTrainingExerciseName.text = trainingExercise.name
            binding.tvTrainingExEquipment.text =
                binding.root.context.getString(trainingExercise.equipment.stringResource)
            binding.tvTrainingExMuscularG.text =
                binding.root.context.getString(trainingExercise.muscularGroup.stringResource)
            binding.tvTrainingExSetsView.visibility = View.GONE

            Glide.with(binding.root.context)
                .load(trainingExercise.equipment.iconResource)
                .into(binding.ivTrainingExEquipmentIcon)

            Glide.with(binding.root.context)
                .load(trainingExercise.muscularGroup.iconResource)
                .into(binding.ivTrainingExMuscularGIcon)

            val image = trainingExercise.image.ifBlank {
                R.drawable.logo
            }

            Glide.with(binding.root.context).setImageRoundedBorders(image, binding.ivTrainingExercise)
        }


    }
}