package com.sergio.gymlog.ui.main.training.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.training.TrainingSet
import com.sergio.gymlog.databinding.SetItemBinding

class NestedTrainingDetailsAdapter(
    private val setsList: List<TrainingSet>
) : RecyclerView.Adapter<NestedTrainingDetailsAdapter.NestedTrainingDetailsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedTrainingDetailsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.set_item, parent, false)
        return NestedTrainingDetailsHolder(view)
    }

    override fun getItemCount() = setsList.size

    override fun onBindViewHolder(holder: NestedTrainingDetailsHolder, position: Int) {
        holder.binSet(setsList[position])
    }


    inner class NestedTrainingDetailsHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val binding = SetItemBinding.bind(view)

        fun binSet(set : TrainingSet) {
            binding.tvSetNumber.text = (layoutPosition+1).toString()
            binding.tvSetRepetitions.text = set.repetitions.toString()
            binding.tvSetWeight.text = set.weight.toString()

            if (set.bodyWeight){

                binding.ivBodyWeightChecked.visibility = View.VISIBLE

            }

        }

    }

}