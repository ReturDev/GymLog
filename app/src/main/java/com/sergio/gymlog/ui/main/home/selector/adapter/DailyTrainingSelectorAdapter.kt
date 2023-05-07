package com.sergio.gymlog.ui.main.home.selector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
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

        fun bind(training : Training){

            binding.tvTrainingName.text = training.name
            binding.tvMucularGroups.text = training.muscularGroups.toString()
            binding.tvExercisesNumber.text = binding.root.context.getString(R.string.number_of_exercises)

            if (selectedTrainingPos != layoutPosition){
                binding.ivDailyTrainingSelected.visibility = View.GONE
            }else{
                binding.ivDailyTrainingSelected.visibility = View.VISIBLE
            }

            binding.root.setOnClickListener {

                manageSelectionTraining()

                onClickTraining()
            }

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