package com.sergio.gymlog.ui.main.training.dailytraining.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.TrainingItemBinding
import java.util.*

class ModifyDailyTrainingAdapter(
    var trainingList : List<Training>,
    private val onClickElement : () -> Unit,
    private val onClickElementSelectionMode: () -> Unit
) : RecyclerView.Adapter<ModifyDailyTrainingAdapter.MDTHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MDTHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_item, parent, false)
        return MDTHolder(view)
    }

    override fun getItemCount() = trainingList.size

    override fun onBindViewHolder(holder: MDTHolder, position: Int) {
        holder.bind(trainingList[position])
    }

    class MDTHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingItemBinding.bind(view)

        fun bind(training: Training){
            binding.tvTrainingName.text = training.name
            binding.tvMucularGroups.text = training.exercises.map {it.muscularGroup }.groupByTo(
                EnumMap(MuscularGroup::class.java),{it},{it.toString()}).values.toString()
            binding.tvExercisesNumber.text = binding.root.context.getString(R.string.number_of_exercises,training.exercises.size)

        }


    }

}