package com.sergio.gymlog.ui.main.training.editor.adapter

import android.annotation.SuppressLint
import android.text.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.TrainingExerciseSet
import com.sergio.gymlog.databinding.TrainingEditorSetItemBinding

class NestedTrainingEditorAdapter(
    val exerciseSets : MutableList<TrainingExerciseSet>,
    private val onDeleteClick : (Int) -> Unit,
    private val onExerciseSetValueChanged : (Int, TrainingExerciseSet) -> Unit
) : RecyclerView.Adapter<NestedTrainingEditorAdapter.NestedTrainingEditorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedTrainingEditorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.training_editor_set_item, parent, false)
        return NestedTrainingEditorHolder(view)
    }

    override fun getItemCount() = exerciseSets.size

    override fun onBindViewHolder(holder: NestedTrainingEditorHolder, position: Int) {
        holder.bind(exerciseSets[position],onDeleteClick, onExerciseSetValueChanged)
    }

    inner class NestedTrainingEditorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingEditorSetItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(exerciseSet: TrainingExerciseSet, onDeleteClick : (Int) -> Unit, onExerciseSetValueChanged: (Int, TrainingExerciseSet) -> Unit){

            val arrayAdapter = ArrayAdapter(binding.root.context, R.layout.training_editor_set_item, arrayOf(1..30))
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spSetRepetitions.adapter = arrayAdapter

            binding.tvTrainingMSetNumber.text = "${layoutPosition+1}"

            binding.etSetWeight.text = SpannableStringBuilder(exerciseSet.weight.toString())

            binding.ivDeteleSet.setOnClickListener {

                onDeleteClick(layoutPosition)

            }

            setListeners(exerciseSet,onExerciseSetValueChanged)

        }

        private fun setListeners(exerciseSet: TrainingExerciseSet, onExerciseSetValueChanged: (Int, TrainingExerciseSet) -> Unit) {

            binding.spSetRepetitions.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                    onValueChanged(onExerciseSetValueChanged)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

            binding.etSetWeight.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->

                if (!hasFocus){

                    if (binding.etSetWeight.text.isBlank()){

                        binding.etSetWeight.text = SpannableStringBuilder("0")

                    }

                    if (binding.etSetWeight.text.toString().toDouble() != exerciseSet.weight){
                        onValueChanged(onExerciseSetValueChanged)
                    }

                }else{
                    if (binding.etSetWeight.text.isBlank()){

                        binding.etSetWeight.text = SpannableStringBuilder()

                    }
                }

            }

            binding.etSetWeight.filters = arrayOf(createWeightFilter())


        }

        private fun onValueChanged(onExerciseSetValueChanged: (Int, TrainingExerciseSet) -> Unit) {

            onExerciseSetValueChanged(layoutPosition, getDataTrainingExerciseSet())

        }

        private fun getDataTrainingExerciseSet() = TrainingExerciseSet(
                repetitions = binding.spSetRepetitions.selectedItem as Int,
                weight = binding.etSetWeight.text.toString().toDouble(),
                bodyWeight = binding.cbBodyWeight.isChecked
            )


        private fun createWeightFilter() = InputFilter { source, start, end, dest, dstart, dend ->

            val sb = StringBuilder(dest)
            sb.replace(dstart, dend, source.subSequence(start, end).toString())
            val temp = sb.toString()

            if (temp == ".") {
                return@InputFilter "0."
            }else if (temp.isNotBlank() && temp[0] == '0' && temp.length > 1 && temp[1] != '.'){
                binding.etSetWeight.text = SpannableStringBuilder(source)
                binding.etSetWeight.setSelection(end)

            }

            val indexPoint = temp.indexOf(".")
            if (indexPoint == -1) {
                if (temp.length == 4 ) {
                    return@InputFilter ".$source"
                }else if (temp.length > 5){
                    return@InputFilter ""
                }
            } else {
                val intPart = temp.substring(0, indexPoint)
                val decimalPart = temp.substring(indexPoint + 1)

                if (intPart.length > 3 || decimalPart.length > 2) {
                    return@InputFilter ""
                }
            }

            return@InputFilter null
        }

    }

}