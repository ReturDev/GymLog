package com.sergio.gymlog.ui.main.home.selector

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Timestamp
import com.sergio.gymlog.data.model.training.Training
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.databinding.DialogDailyTrainingSelectorBinding
import com.sergio.gymlog.ui.main.home.HomeFragmentDirections
import com.sergio.gymlog.ui.main.home.selector.adapter.DailyTrainingSelectorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class DailyTrainingSelectorDialog(
    private val listener: DailyTrainingSelectionListener,
    private val trainingSelected: Training?
) : BottomSheetDialogFragment() {

    private lateinit var binding : DialogDailyTrainingSelectorBinding

    private lateinit var adapter: DailyTrainingSelectorAdapter
    private val dailyTrainingSelectorViewModel by viewModels<DailyTrainingSelectorViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogDailyTrainingSelectorBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            (it as BottomSheetDialog).let { bottomSheetDialog ->
                (bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).let { frameLayout ->
                    BottomSheetBehavior.from(frameLayout).peekHeight = (Resources.getSystem().displayMetrics.heightPixels * 0.7).toInt()
                    BottomSheetBehavior.from(frameLayout).state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

        }


        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sheetContainer = requireView().parent as ViewGroup
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        dailyTrainingSelectorViewModel.loadTrainings()
        initRecyclerView()
        setCollector()
        setListeners()


    }

    private fun initRecyclerView() {
        adapter = DailyTrainingSelectorAdapter(mutableListOf()) { position ->
            onClickTraining(
                position
            )
        }
        val recycler = binding.rvDailyTrainingSelectorList
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                dailyTrainingSelectorViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.rvDailyTrainingSelectorList.visibility = View.GONE
                        binding.dailyTrainingSelectorEmptyListRoot.visibility = View.GONE
                        binding.pbDailyTrainingSelectorLoading.visibility = View.VISIBLE

                    }
                    if (currentState.loaded){

                        binding.pbDailyTrainingSelectorLoading.visibility = View.GONE

                        if (currentState.trainings.isEmpty()){
                            binding.dailyTrainingSelectorEmptyListRoot.visibility = View.VISIBLE
                            binding.rvDailyTrainingSelectorList.visibility = View.GONE
                        }else{
                            binding.rvDailyTrainingSelectorList.visibility = View.VISIBLE
                            binding.dailyTrainingSelectorEmptyListRoot.visibility = View.GONE
                        }

                        if (trainingSelected != null){
                            adapter.setSelectedTrainingPos(currentState.trainings.indexOfFirst { t -> t == trainingSelected })
                        }

                        adapter.trainings.addAll(currentState.trainings)
                        adapter.notifyDataSetChanged()


                    }

                }

            }
        }
    }

    private fun setListeners() {

        binding.btnDailyTrainingSelectorNewTraining.setOnClickListener {
            val action = HomeFragmentDirections.actionGlobalTrainingEditorFragment(emptyArray())
            findNavController().navigate(action)
            dialog?.dismiss()
        }

    }

    private fun onClickTraining(position : Int){

        if (position == -1){
            listener.onTrainingSelected(null)
        }else{
            listener.onTrainingSelected(UserInfo.DailyTraining(date = Timestamp(Date()), training = adapter.trainings[position]))
        }

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener.saveDailyTraining()
    }

}