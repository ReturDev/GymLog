package com.sergio.gymlog.ui.main.home.progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.ExerciseItem
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.databinding.StartedDailyTrainingExerciseItemBinding
import com.sergio.gymlog.ui.main.training.detail.adapter.NestedTrainingDetailsAdapter
import com.sergio.gymlog.util.extension.setImageRoundedBorders

class DailyTrainingProgressAdapter(
    val trainingExercises : List<ExerciseItem>,
    private val onClickElement : (Int) -> Unit
) : RecyclerView.Adapter<DailyTrainingProgressAdapter.DailyTrainingProgressHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTrainingProgressHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.started_daily_training_exercise_item, parent, false)
        return DailyTrainingProgressHolder(view)
    }

    override fun getItemCount() = trainingExercises.size

    override fun onBindViewHolder(holder: DailyTrainingProgressHolder, position: Int) {

        holder.bind(trainingExercises[position])

    }

    inner class DailyTrainingProgressHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val binding = StartedDailyTrainingExerciseItemBinding.bind(view)
        private lateinit var adapter : NestedTrainingDetailsAdapter

        fun bind(trainingExercise: ExerciseItem) {

            binding.tvStartedTrainingExerciseName.text = trainingExercise.exercise.name
            binding.tvStartedTrainingExerciseEquipment.text =
                binding.root.context.getString(trainingExercise.exercise.equipment.stringResource)
            binding.tvStartedTrainingExerciseExMuscularG.text =
                binding.root.context.getString(trainingExercise.exercise.muscularGroup.stringResource)

            Glide.with(binding.root.context)
                .load(trainingExercise.exercise.equipment.iconResource)
                .into(binding.ivStartedTrainingExEquipmentIcon)

            Glide.with(binding.root.context)
                .load(trainingExercise.exercise.muscularGroup.iconResource)
                .into(binding.ivStartedTrainingExMuscularGIcon)

            val image = trainingExercise.exercise.image.ifBlank {
                R.drawable.logo
            }

            Glide.with(binding.root.context).setImageRoundedBorders(image, binding.ivStartedTrainingExerciseImage)

            manageItemSelected(trainingExercise)

            initNestedRecycler((trainingExercise.exercise as Exercises.TrainingExercise).sets)
            setListeners()
        }

        private fun setListeners() {
            binding.btnStartedTrainingExerciseInvisibleSets.setOnClickListener {

                it.visibility = View.GONE
                binding.startedTrainingExerciseSetsLayout.visibility = View.VISIBLE

            }

            binding.tvStartedTrainingExerciseVisibleSets.setOnClickListener {

                binding.btnStartedTrainingExerciseInvisibleSets.visibility = View.VISIBLE
                binding.startedTrainingExerciseSetsLayout.visibility = View.GONE

            }

            binding.root.setOnClickListener {
                onClickElement(layoutPosition)
            }


        }

        private fun manageItemSelected(exerciseItem: ExerciseItem){

            if (exerciseItem.selected){

                binding.ivStartedTrainingExerciseExMuscularGSelected.visibility = View.VISIBLE

            }else{

                binding.ivStartedTrainingExerciseExMuscularGSelected.visibility = View.GONE

            }

        }

        private fun initNestedRecycler(sets : List<TrainingExerciseSet>){

            adapter = NestedTrainingDetailsAdapter(sets)
            val recycler = binding.startedTrainingExerciseSetsContainer.rvSetsContainerSetsList
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        }


    }


    }