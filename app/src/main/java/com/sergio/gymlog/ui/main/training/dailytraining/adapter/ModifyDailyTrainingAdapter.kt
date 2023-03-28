package com.sergio.gymlog.ui.main.training.dailytraining.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.data.model.MuscularGroup
import com.sergio.gymlog.data.model.Training
import com.sergio.gymlog.databinding.FragmentTrainingItemBinding
import java.util.*
import kotlin.collections.HashMap

class ModifyDailyTrainingAdapter(
    var trainingList : List<Training>,
    private val onClickElement : () -> Unit,
    private val onClickElementSelectionMode: () -> Unit
) : RecyclerView.Adapter<ModifyDailyTrainingAdapter.MDTHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MDTHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_training_item, parent, false)
        return MDTHolder(view)
    }

    override fun getItemCount() = trainingList.size

    override fun onBindViewHolder(holder: MDTHolder, position: Int) {
        holder.bind(trainingList[position])
    }

    class MDTHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = FragmentTrainingItemBinding.bind(view)

        fun bind(training: Training){
            binding.tvTrainingName.text = training.name
            binding.tvTrainginDescription.text = training.description
            binding.tvMucularGroups.text = training.exercises.map {it.muscularGroup }.groupByTo(
                EnumMap(MuscularGroup::class.java),{it},{it.name.lowercase().first().uppercase()}).values.toString()
            binding.tvExercisesNumber.text = binding.root.context.getString(R.string.number_of_exercises,training.exercises.size)

        }


    }

}