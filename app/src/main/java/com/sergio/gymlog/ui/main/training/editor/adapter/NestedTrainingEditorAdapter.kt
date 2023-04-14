package com.sergio.gymlog.ui.main.training.editor.adapter

import android.annotation.SuppressLint
import android.text.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.bind(exerciseSets[position])
    }

    inner class NestedTrainingEditorHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = TrainingEditorSetItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(exerciseSet: TrainingExerciseSet){

            binding.etSetRepetitions.text = SpannableStringBuilder(exerciseSet.repetitions.toString())

            binding.tvTrainingMSetNumber.text = "${layoutPosition+1}"

            binding.etSetWeight.text = SpannableStringBuilder(exerciseSet.weight.toString())

            binding.cbBodyWeight.isChecked = exerciseSet.bodyWeight

            binding.etSetWeight.isEnabled = !binding.cbBodyWeight.isChecked


            setListeners(exerciseSet)

        }

        private fun setListeners(exerciseSet: TrainingExerciseSet) {

            binding.cbBodyWeight.setOnCheckedChangeListener { _, isChecked ->

                binding.etSetWeight.isEnabled = !isChecked

            }


            binding.ivDeteleSet.setOnClickListener {

                onDeleteClick(layoutPosition)

            }

            binding.etSetRepetitions.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->

                if (!hasFocus){

                    if (binding.etSetRepetitions.text.isBlank()){

                        binding.etSetRepetitions.text = SpannableStringBuilder("0")

                    }

                }

            }

            binding.etSetRepetitions.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {

                    val text = s.toString()

                    if (text.isNotBlank()){

                        if (text.toInt() != exerciseSet.repetitions){

                            onValueChanged(onExerciseSetValueChanged)

                        }

                    }

                }


            })

            binding.etSetWeight.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {

                    val text = s.toString()

                    if (text.isNotBlank()){

                        if (text.toDouble() != exerciseSet.weight){

                            onValueChanged(onExerciseSetValueChanged)

                        }

                    }

                }

            })

            binding.etSetWeight.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->

                if (!hasFocus){

                    if (binding.etSetWeight.text.isBlank()){

                        binding.etSetWeight.text = SpannableStringBuilder("0")

                    }else if (binding.etSetWeight.text.indexOf(".") == -1){

                        binding.etSetWeight.text = binding.etSetWeight.text.append(".0")

                    }else if (binding.etSetWeight.text.indexOf(".") == 0){
                        binding.etSetWeight.text = binding.etSetWeight.text.insert(0,"0")
                    }


                    if (binding.etSetWeight.text.toString().toDouble() != exerciseSet.weight){

                        onValueChanged(onExerciseSetValueChanged)

                    }

                }

            }

            binding.etSetRepetitions.filters = arrayOf(createRepetitionsFilter())
            binding.etSetWeight.filters = arrayOf(createWeightFilter())


        }

        private fun onValueChanged(onExerciseSetValueChanged: (Int, TrainingExerciseSet) -> Unit) {

            onExerciseSetValueChanged(layoutPosition, getDataTrainingExerciseSet())

        }

        private fun getDataTrainingExerciseSet() = TrainingExerciseSet(
                repetitions = binding.etSetRepetitions.text.toString().toInt(),
                weight = binding.etSetWeight.text.toString().toDouble(),
                bodyWeight = binding.cbBodyWeight.isChecked
            )

        private fun createRepetitionsFilter() = InputFilter { source, start, end, dest, dstart, dend ->

            val sb = StringBuilder(dest)
            sb.replace(dstart, dend, source.subSequence(start, end).toString())
            val temp = sb.toString()

            if (temp.length > 2){
                return@InputFilter ""
            }
            if (temp.isNotBlank() && temp[0] == '0'){
                return@InputFilter ""
            }

            return@InputFilter null

        }

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