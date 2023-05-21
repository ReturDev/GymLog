package com.sergio.gymlog.ui.main.training.detail.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.databinding.TrainingExerciseItemBinding
import com.sergio.gymlog.util.extension.setImageRoundedBorders

class TrainingDetailsAdapter(
    private val trainingExercises : MutableList<Exercises.TrainingExercise>
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
             binding.tvTrainingExEquipment.text = binding.root.context.getString(trainingExercise.equipment.stringResource)
             binding.tvTrainingExMuscularG.text = binding.root.context.getString(trainingExercise.muscularGroup.stringResource)

             val alpha = if (trainingExercise.image.isBlank()){
                 0.75f
             }else{
                 1f
             }

             val image = trainingExercise.image.ifBlank {
                 R.drawable.logo
             }

             Glide.with(binding.root.context).setImageRoundedBorders(image, binding.ivTrainingExercise)
             binding.ivTrainingExercise.alpha = alpha

             Glide.with(binding.root.context)
                 .load(trainingExercise.equipment.iconResource)
                 .into(binding.ivTrainingExEquipmentIcon)

             Glide.with(binding.root.context)
                 .load(trainingExercise.muscularGroup.iconResource)
                 .into(binding.ivTrainingExMuscularGIcon)

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
            val recycler = binding.trainingExSetsContainer.rvSetsContainerSetsList
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        }


    }
    
}