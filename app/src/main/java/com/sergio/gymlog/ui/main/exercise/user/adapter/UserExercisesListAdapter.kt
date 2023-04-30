package com.sergio.gymlog.ui.main.exercise.user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.databinding.ExerciseItemBinding

class UserExercisesListAdapter(
    val userExercises : MutableList<Exercises.UserExercise>,
    private val onClickExercise : (Int) -> Unit
) : RecyclerView.Adapter<UserExercisesListAdapter.UserExercisesListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserExercisesListViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false)
        return UserExercisesListViewHolder(view)

    }

    override fun getItemCount() = userExercises.size

    override fun onBindViewHolder(holder: UserExercisesListViewHolder, position: Int) {

        holder.bind(userExercises[position])


    }

    inner class UserExercisesListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val binding = ExerciseItemBinding.bind(view)

        fun bind(userExercise: Exercises.UserExercise){

            binding.tvExerciseName.text = userExercise.name
            binding.tvExerciseEquipment.text = binding.root.context.getString(userExercise.equipment.stringResource)
            binding.tvExerciseMuscularGroup.text = binding.root.context.getString(userExercise.muscularGroup.stringResource)

            setListeners()

        }

        private fun setListeners() {
            binding.root.setOnClickListener {

                onClickExercise(layoutPosition)

            }
        }

    }

}