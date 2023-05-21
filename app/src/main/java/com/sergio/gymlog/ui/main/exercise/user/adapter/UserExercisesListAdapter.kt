package com.sergio.gymlog.ui.main.exercise.user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.databinding.ExerciseItemBinding
import com.sergio.gymlog.util.extension.setImageRoundedBorders

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

            Glide.with(binding.root.context)
                .load(userExercise.equipment.iconResource)
                .into(binding.ivExerciseEquipmentIcon)

            Glide.with(binding.root.context)
                .load(userExercise.muscularGroup.iconResource)
                .into(binding.ivExerciseMuscularGIcon)

            val alpha = if (userExercise.image.isBlank()){
                0.75f
            }else{
                1f
            }

            val image = userExercise.image.ifBlank {
                R.drawable.logo
            }

            Glide.with(binding.root.context).setImageRoundedBorders(image, binding.ivExerciseImage)
            binding.ivExerciseImage.alpha = alpha

            setListeners()

        }

        private fun setListeners() {
            binding.root.setOnClickListener {

                onClickExercise(layoutPosition)

            }
        }

    }

}