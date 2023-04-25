package com.sergio.gymlog.ui.main.training.editor.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.Equipment
import com.sergio.gymlog.data.model.exercise.Exercises
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.databinding.TrainingEditorExerciseItemBinding

class TrainingEditorAdapter(
    val trainingExercises : MutableList<Exercises.TrainingExercise>,
    private val onRemoveExercise : (exercisePos : Int) -> Unit,
    private val onAddExerciseSet : (exercisePos : Int) -> Unit,
    private val onDeleteExerciseSet : (exercisePos : Int, exerciseSetPos : Int) -> Unit,
    private val onExerciseSetValueChanged : (exerciseID : String ,exerciseSetPos : Int, trainingSet : TrainingExerciseSet) -> Unit
) : RecyclerView.Adapter<TrainingEditorAdapter.TrainingEditorHolder>() {

    private var itemShowingExerciseSetsPos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingEditorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_editor_exercise_item, parent, false)
        return TrainingEditorHolder(view)
    }

    override fun getItemCount() = trainingExercises.size

    override fun onBindViewHolder(holder: TrainingEditorHolder, position: Int) {

        holder.bind(trainingExercises[position])

    }

    inner class TrainingEditorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingEditorExerciseItemBinding.bind(view)
        lateinit var adapter : NestedTrainingEditorAdapter

        fun bind(
            trainingExercise: Exercises.TrainingExercise
        ){

            binding.ivTrainingEditorExImage.setImageURI(trainingExercise.image.toUri())
            binding.tvTrainingEditorExName.text = trainingExercise.name
            binding.tvTrainingEditorExEquipment.text = trainingExercise.equipment.toString()
            binding.tvTrainingEditorExMuscularGroup.text = trainingExercise.muscularGroup.toString()
            binding.rvTrainingEditorExSets.visibility = if (layoutPosition != itemShowingExerciseSetsPos) View.GONE else View.VISIBLE

            initNestedRecycler(trainingExercise)

            binding.tvTrainingEditorExSetsQuantity.text = binding.root.context.getString(R.string.exercise_sets_quantity, adapter.exerciseSets.size)

            setListeners()

        }

        private fun initNestedRecycler(trainingExercise : Exercises.TrainingExercise){
            adapter = NestedTrainingEditorAdapter(
                exerciseSets = trainingExercise.sets.toMutableList(),
                bodyWeightExerciseSets = trainingExercise.equipment == Equipment.BODY_WEIGHT,
                onDeleteClick = { exerciseSetPos -> onDeleteExerciseSet(layoutPosition,exerciseSetPos)},
                onExerciseSetValueChanged = { exerciseSetPos,
                                              trainingSet ->
                    onExerciseSetValueChanged(
                        trainingExercise.id,
                        exerciseSetPos, trainingSet
                    )
                }
            )
            val recyclerView = binding.rvTrainingEditorExSets
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        }


        private fun setListeners() {

            binding.ivTrainingEditorExRemove.setOnClickListener { onRemoveExercise(layoutPosition) }
            binding.btnTrainingEditorExAddSet.setOnClickListener { onAddExerciseSet(layoutPosition) }
            binding.tvTrainingEditorExSetsQuantity.setOnClickListener { showExerciseSets()}

        }

        fun deleteExerciseSet(exerciseSetPos: Int){

            adapter.exerciseSets.removeAt(exerciseSetPos)
            adapter.notifyItemRemoved(exerciseSetPos)

            if (adapter.exerciseSets.size == 1){
                adapter.notifyItemChanged(0,Any())
            }

            if (exerciseSetPos != adapter.exerciseSets.size){
                adapter.notifyItemRangeChanged(exerciseSetPos ,adapter.exerciseSets.size)
            }
            binding.tvTrainingEditorExSetsQuantity.text = binding.root.context.getString(R.string.exercise_sets_quantity, adapter.exerciseSets.size)

        }

        private fun showExerciseSets(){

            when (itemShowingExerciseSetsPos) {
                layoutPosition -> {
                    binding.rvTrainingEditorExSets.visibility = View.GONE
                    itemShowingExerciseSetsPos = -1
                }
                -1 -> {
                    binding.rvTrainingEditorExSets.visibility = View.VISIBLE
                    itemShowingExerciseSetsPos = layoutPosition
                }
                else -> {
                    val changeItemPos = itemShowingExerciseSetsPos
                    itemShowingExerciseSetsPos = layoutPosition
                    notifyItemChanged(changeItemPos)
                    binding.rvTrainingEditorExSets.visibility = View.VISIBLE

                }
            }


        }

        fun addExerciseSet(exerciseSets : TrainingExerciseSet){

            adapter.exerciseSets.add(exerciseSets)

            binding.tvTrainingEditorExSetsQuantity.text = binding.root.context.getString(R.string.exercise_sets_quantity, adapter.exerciseSets.size)
            if (adapter.exerciseSets.size == 2){
                adapter.notifyItemChanged(0)
            }

            adapter.notifyItemInserted(adapter.exerciseSets.size -1)

        }

    }


}