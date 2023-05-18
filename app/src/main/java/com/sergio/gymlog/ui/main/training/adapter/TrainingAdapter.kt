package com.sergio.gymlog.ui.main.training.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.TrainingItemBinding

class TrainingAdapter(
    var trainingList : List<Training>,
    private val onClickElement : (String) -> Unit,
    private val onLongClickTraining : (Int) -> Unit
) : RecyclerView.Adapter<TrainingAdapter.TrainingHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_item, parent, false)
        return TrainingHolder(view)
    }

    override fun getItemCount() = trainingList.size

    override fun onBindViewHolder(holder: TrainingHolder, position: Int) {

        holder.bind(trainingList[position])


    }

    inner class TrainingHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingItemBinding.bind(view)

        fun bind(training : Training){

            binding.tvTrainingName.text = training.name
            val textMuscularGroups = training.muscularGroups.map { ex -> binding.root.context.getString(ex.stringResource)}.toString()
            binding.tvMucularGroups.text = textMuscularGroups.substring(startIndex = 1, endIndex = textMuscularGroups.length-1)
            binding.tvExercisesNumber.text = binding.root.context.getString(R.string.number_of_exercises,training.exercises.size)


            binding.root.setOnClickListener { onClickElement(training.id) }
            binding.trainingCard.setOnLongClickListener {
                onLongClickTraining(layoutPosition)
                true
            }


        }



    }



}