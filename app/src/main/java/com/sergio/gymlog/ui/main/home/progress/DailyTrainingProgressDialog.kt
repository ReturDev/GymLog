package com.sergio.gymlog.ui.main.home.progress

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.training.TrainingOfTrainingLog
import com.sergio.gymlog.databinding.DialogDailyTrainingProgressBinding
import com.sergio.gymlog.ui.main.home.logsaver.LogSaverDialog
import com.sergio.gymlog.ui.main.home.logsaver.SaveLogInterface
import kotlinx.coroutines.launch

class DailyTrainingProgressDialog(
    private val training : Training,
    private val saveLogInterface: SaveLogInterface
) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogDailyTrainingProgressBinding
    private lateinit var adapter : DailyTrainingProgressAdapter

    private val dailyTrainingProgressViewModel by viewModels<DailyTrainingProgressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogDailyTrainingProgressBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

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

        dailyTrainingProgressViewModel.setExercises(training.exercises)
        setListeners()
        setCollector()

    }



    private fun setListeners(){

        binding.btnDailyTrainingProgressDialogComplete.setOnClickListener {
            dailyTrainingProgressViewModel.saveExercisesSelected()
        }

    }

    private fun setCollector(){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dailyTrainingProgressViewModel.uiState.collect{ currentState ->

                    if (currentState.loaded){

                        initRecyclerView()
                        dailyTrainingProgressViewModel.resetStateValues()
                        binding.btnDailyTrainingProgressDialogComplete.isEnabled = currentState.exercisesSelectedQuantity != 0

                    }

                    if (currentState.exerciseChangedPosition != -1){

                        adapter.notifyItemChanged(currentState.exerciseChangedPosition)
                        dailyTrainingProgressViewModel.resetStateValues()
                        binding.btnDailyTrainingProgressDialogComplete.isEnabled = currentState.exercisesSelectedQuantity != 0

                    }

                    if (currentState.exercisesToAdd.isNotEmpty()){

                        LogSaverDialog(training.copy(exercises = currentState.exercisesToAdd), object : SaveLogInterface{
                            override fun saveLog(trainingOfTrainingLog: TrainingOfTrainingLog) {
                                saveLogInterface.saveLog(trainingOfTrainingLog)
                                dismiss()
                            }

                        }).show(parentFragmentManager, "log_saver_dialog")

                        dailyTrainingProgressViewModel.resetStateValues()

                    }

                }
            }
        }

    }

    private fun initRecyclerView() {

        adapter = DailyTrainingProgressAdapter(dailyTrainingProgressViewModel.uiState.value.exercises) { position -> onClickExercise(position) }
        val recyclerView = binding.rvDailyTrainingProgressDialogExercises
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private fun onClickExercise(position : Int){

        if (adapter.trainingExercises[position].selected){

            dailyTrainingProgressViewModel.deselectExercise(position)

        }else{

            dailyTrainingProgressViewModel.selectExercise(position)

        }

    }





}