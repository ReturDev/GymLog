package com.sergio.gymlog.ui.main.record.log

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.training.TrainingLog
import com.sergio.gymlog.databinding.DialogLogDetailsBinding
import com.sergio.gymlog.ui.main.training.detail.adapter.TrainingDetailsAdapter


class LogDetailsDialog (
    private val trainingLog : TrainingLog
): BottomSheetDialogFragment() {

    private lateinit var binding : DialogLogDetailsBinding
    private lateinit var adapter : TrainingDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLogDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)

        dialog.setTitle(trainingLog.date.toString())

        dialog.setOnShowListener {
            (it as BottomSheetDialog).let { bottomSheetDialog ->
                (bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).let { frameLayout ->
                    BottomSheetBehavior.from(frameLayout).peekHeight = Resources.getSystem().displayMetrics.heightPixels
                    BottomSheetBehavior.from(frameLayout).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sheetContainer = requireView().parent as ViewGroup
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        bind()
        initRecyclerView()
    }

    private fun bind() {
        binding.tvLogDetailsDialogTitle.text = trainingLog.date!!.toDate().toString()
        binding.tvLogDetailsDialogTrainingName.text = trainingLog.training!!.training!!.name
        binding.tvLogDetailsDialogTrainingDesc.text = trainingLog.training.training!!.description
        binding.tvLogDetailsDialogTrainingRemarks.text = trainingLog.training.remarks
        binding.tvLogDetailsDialogExercisesNumber.text = getString(R.string.number_of_exercises, trainingLog.training.training.exercises.size)
    }

    private fun initRecyclerView() {

        adapter = TrainingDetailsAdapter(trainingLog.training!!.training!!.exercises.toMutableList())
        val recyclerView = binding.rvLogDetailsDialogExercises
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

}