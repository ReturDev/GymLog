package com.sergio.gymlog.ui.main.home.logsaver

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.training.TrainingOfTrainingLog
import com.sergio.gymlog.databinding.DialogLogSaverBinding
import com.sergio.gymlog.ui.main.training.detail.adapter.TrainingDetailsAdapter
import com.sergio.gymlog.util.extension.alertDialog


class LogSaverDialog(
    private val training : Training,
    private val saveLogInterface: SaveLogInterface
) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogLogSaverBinding
    private lateinit var adapter: TrainingDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLogSaverBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog =  super.onCreateDialog(savedInstanceState).apply {
            setOnKeyListener { _, keyCode, event ->

                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP){

                    cancelDialog()

                    return@setOnKeyListener true

                }

                return@setOnKeyListener false

            }

        }

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

        val behavior = BottomSheetBehavior.from(view.parent as View)


        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED){
                    if (binding.etLogSaverRemarks.text.isNotBlank()){
                        cancelDialog()
                    }
                }

            }


            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < -0.5){

                    if (binding.etLogSaverRemarks.text.isNotBlank()){

                        behavior.state = BottomSheetBehavior.STATE_EXPANDED

                    }

                }
            }


        })

        bind()
        initRecyclerView()
        setListeners()

    }

    private fun bind(){

        binding.tvLogSaverName.text = training.name
        binding.tvLogSaverDescription.text = training.description

    }

    private fun initRecyclerView(){

        adapter = TrainingDetailsAdapter(training.exercises.toMutableList())
        val recycler = binding.rvLogSaverExercises
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private fun setListeners() {
        binding.btnLogSaverCancel.setOnClickListener {
            cancelDialog()
        }

        binding.btnLogSaverSave.setOnClickListener {
            saveLogInterface.saveLog(createTrainingOfTrainingLog())
            dismiss()
        }

    }

    private fun cancelDialog(){

        if (binding.etLogSaverRemarks.text.isNotBlank()){

            AlertDialog.Builder(requireContext()).alertDialog(
                {alertDialog, _ ->

                    alertDialog.dismiss()
                    this@LogSaverDialog.dismiss()

                },
                {alertDialog, _ ->
                    alertDialog.dismiss()
                }
            )

        }else{
            this.dismiss()
        }

    }

    private fun createTrainingOfTrainingLog() : TrainingOfTrainingLog{
        return TrainingOfTrainingLog(training = training, remarks = binding.etLogSaverRemarks.text.toString().trim())
    }

}