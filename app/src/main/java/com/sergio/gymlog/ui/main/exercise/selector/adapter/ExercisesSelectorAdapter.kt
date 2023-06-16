package com.sergio.gymlog.ui.main.exercise.selector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.databinding.ExerciseItemBinding
import com.sergio.gymlog.util.extension.setImageRoundedBorders

class ExercisesSelectorAdapter(
    var exercisesList : List<ExerciseItem>,
    private val onClickElement : (Int) -> Unit,
    private val onLongClickExercise : (Int) -> Unit
) : RecyclerView.Adapter<ExercisesSelectorAdapter.ExercisesSelectorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesSelectorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false)
        return ExercisesSelectorHolder(view)
    }

    override fun getItemCount() = exercisesList.size

    override fun onBindViewHolder(holder: ExercisesSelectorHolder, position: Int) {
        holder.bind(exercisesList[position])
    }


    inner class ExercisesSelectorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = ExerciseItemBinding.bind(view)

        fun bind(exerciseItem : ExerciseItem){
            val exercise = exerciseItem.exercise
            manageItemSelected(exerciseItem)
            binding.tvExerciseName.text = exercise.name
            binding.ivExerciseImage.setImageURI(exercise.image.toUri())
            binding.tvExerciseMuscularGroup.text = binding.root.context.getString(exercise.muscularGroup.stringResource)
            binding.tvExerciseEquipment.text = binding.root.context.getString(exercise.equipment.stringResource)

            Glide.with(binding.root.context)
                .load(exercise.equipment.iconResource)
                .into(binding.ivExerciseEquipmentIcon)

            Glide.with(binding.root.context)
                .load(exercise.muscularGroup.iconResource)
                .into(binding.ivExerciseMuscularGIcon)

            val alpha = if (exercise.image.isBlank()){
                0.75f
            }else{
                1f
            }

            val image = exercise.image.ifBlank {
                R.drawable.logo
            }

            Glide.with(binding.root.context)
                .load(image)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(20)
                    )
                )
                .into(binding.ivExerciseImage)

            setListeners()

        }

        private fun setListeners() {

            binding.selectionExerciseCard.setOnClickListener {

                onClickElement(layoutPosition)

            }

            binding.selectionExerciseCard.setOnLongClickListener {
                onLongClickExercise(layoutPosition)
                true
            }

        }

        private fun manageItemSelected(exerciseItem: ExerciseItem){

            if (exerciseItem.selected){

                binding.ivExerciseSelected.visibility = View.VISIBLE

            }else{

                binding.ivExerciseSelected.visibility = View.GONE

            }

        }


    }

}