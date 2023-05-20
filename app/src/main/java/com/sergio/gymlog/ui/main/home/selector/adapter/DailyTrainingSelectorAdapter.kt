package com.sergio.gymlog.ui.main.home.selector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.databinding.TrainingItemBinding

class DailyTrainingSelectorAdapter(
    val trainings: MutableList<Training>,
    private val onClickTraining: (position : Int) -> Unit
) : RecyclerView.Adapter<DailyTrainingSelectorAdapter.DailyTrainingSelectorHolder>() {

    private var selectedTrainingPos : Int = -1

    fun setSelectedTrainingPos(value : Int){
        selectedTrainingPos = value
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyTrainingSelectorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_item, parent, false)
        return DailyTrainingSelectorHolder(view)
    }

    override fun onBindViewHolder(
        holder: DailyTrainingSelectorHolder,
        position: Int
    ) {
        holder.bind(trainings[position])
    }

    override fun getItemCount() = trainings.size

    inner class DailyTrainingSelectorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingItemBinding.bind(view)
        private lateinit var iconAdapter : IconAdapter

        fun bind(training : Training){

            binding.tvTrainingName.text = training.name
            binding.tvExercisesNumber.text = binding.root.context.getString(R.string.number_of_exercises, training.exercises.size)

            if (selectedTrainingPos != layoutPosition){
                binding.ivDailyTrainingSelected.visibility = View.GONE
            }else{
                binding.ivDailyTrainingSelected.visibility = View.VISIBLE
            }

            initIconRecycler(training.muscularGroups)

            binding.root.setOnClickListener {

                manageSelectionTraining()

                onClickTraining()
            }

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

        private fun onClickTraining(){

            this@DailyTrainingSelectorAdapter.onClickTraining(selectedTrainingPos)

        }

        private fun manageSelectionTraining(){

            selectedTrainingPos = if (selectedTrainingPos == layoutPosition){
                -1
            }else if (selectedTrainingPos != -1){
                notifyItemChanged(selectedTrainingPos)
                layoutPosition
            }else {
                layoutPosition
            }

            notifyItemChanged(layoutPosition)

        }

    }

}