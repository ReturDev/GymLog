package com.sergio.gymlog.ui.main.training.detail.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.databinding.TrainingExerciseItemBinding

class TrainingDetailsAdapter(
    private val trainingExercises : List<Exercises.TrainingExercise>
) : RecyclerView.Adapter<TrainingDetailsAdapter.TrainingDetailsHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDetailsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_exercise_item, parent, false)
        return TrainingDetailsHolder(view)
    }

    override fun getItemCount() = trainingExercises.size

    override fun onBindViewHolder(holder: TrainingDetailsHolder, position: Int) {
        
        holder.bind(trainingExercises[position])
        
    }

    inner class TrainingDetailsHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingExerciseItemBinding.bind(view)
        private lateinit var adapter : NestedTrainingDetailsAdapter

         fun bind(trainingExercise: Exercises.TrainingExercise) {

            binding.tvTrainingExerciseName.text = trainingExercise.name
            binding.tvTrainingExEquipment.text = trainingExercise.equipment.toString()
            binding.tvTrainingExMuscularG.text = trainingExercise.muscularGroup.toString()

            initNestedRecycler(trainingExercise.sets)
            setListeners()
        }

        private fun setListeners() {
            binding.tvTrainingExInvisibleSets.setOnClickListener {

                it.visibility = View.GONE
                binding.trainingExSetsLayout.visibility = View.VISIBLE

            }

            binding.tvTrainingExVisibleSets.setOnClickListener {

                binding.tvTrainingExInvisibleSets.visibility = View.VISIBLE
                binding.trainingExSetsLayout.visibility = View.GONE

            }
        }

        private fun initNestedRecycler(sets : List<TrainingExerciseSet>){

            adapter = NestedTrainingDetailsAdapter(sets)
            val recycler = binding.rvTrainingExSets
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        }

    }
    
}