package com.sergio.gymlog.ui.main.training.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.TrainingItemBinding
import com.sergio.gymlog.ui.main.home.selector.adapter.IconAdapter

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
        private lateinit var iconAdapter : IconAdapter

        fun bind(training : Training){

            binding.tvTrainingName.text = training.name
            binding.tvExercisesNumber.text = binding.root.context.getString(R.string.number_of_exercises,training.exercises.size)


            binding.root.setOnClickListener { onClickElement(training.id) }
            binding.trainingCard.setOnLongClickListener {
                onLongClickTraining(layoutPosition)
                true
            }

            initIconRecycler(training.muscularGroups)

        }

        private fun initIconRecycler(muscularGroups: List<MuscularGroup>) {
            iconAdapter = IconAdapter(muscularGroups)
            binding.rvTrainingItemMuscularIcons.adapter = iconAdapter
            binding.rvTrainingItemMuscularIcons.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }


    }



}