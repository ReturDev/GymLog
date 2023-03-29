package com.sergio.gymlog.ui.main.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.Exercises
import com.sergio.gymlog.databinding.ExerciseItemBinding

class ExercisesSelectorAdapter(
    private val exercisesList : List<Exercises>,
    private val onClickElement : (Int, Boolean) -> Unit
) : RecyclerView.Adapter<ExercisesSelectorAdapter.ExercisesSelectorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesSelectorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false)
        return ExercisesSelectorHolder(view)
    }

    override fun getItemCount() = exercisesList.size

    override fun onBindViewHolder(holder: ExercisesSelectorHolder, position: Int) {
        holder.bind(exercisesList[position], onClickElement = onClickElement)
    }


    class ExercisesSelectorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = ExerciseItemBinding.bind(view)

        fun bind(exercise : Exercises, onClickElement: (Int, Boolean) -> Unit){
            binding.tvExerciseName.text = exercise.name
            binding.ivExerciseImage.setImageURI(exercise.image.toUri())
            binding.tvExerciseDescription.text = exercise.description
            binding.tvExerciseMuscularGroup.text = exercise.muscularGroup.toString()

            binding.exerciseCard.setOnClickListener {

                onClickElement(layoutPosition,manageItemSelected())

            }

        }

        private fun manageItemSelected() : Boolean{

            val selected : Boolean

            if (binding.ivChecked.visibility == View.GONE){
                binding.ivChecked.visibility = View.VISIBLE
                selected = true
            }else{
                binding.ivChecked.visibility = View.GONE
                selected = false
            }

            return selected

        }


    }

}