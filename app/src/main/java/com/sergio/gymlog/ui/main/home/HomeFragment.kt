package com.sergio.gymlog.ui.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.training.TrainingOfTrainingLog
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.databinding.FragmentHomeBinding
import com.sergio.gymlog.ui.main.home.logsaver.SaveLogInterface
import com.sergio.gymlog.ui.main.home.progress.DailyTrainingProgressDialog
import com.sergio.gymlog.ui.main.home.selector.DailyTrainingSelectionListener
import com.sergio.gymlog.ui.main.home.selector.DailyTrainingSelectorDialog
import com.sergio.gymlog.ui.main.training.detail.adapter.TrainingDetailsAdapter
import com.sergio.gymlog.util.SpacingItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), DailyTrainingSelectionListener, SaveLogInterface {

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var adapter : TrainingDetailsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        homeViewModel.load()
        initRecyclerView()
        setListeners()
        setCollector()
    }

    private fun initRecyclerView(){

        adapter = TrainingDetailsAdapter(mutableListOf())
        val recycler = binding.rvHomeTrainingExercises
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val spacingDecorator = SpacingItemDecorator(resources.getDimensionPixelSize(R.dimen.recycler_decoration_bottom_spacing))
        recycler.addItemDecoration(spacingDecorator)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {
        lifecycleScope.launch{

            repeatOnLifecycle(Lifecycle.State.STARTED){

                homeViewModel.uiState.collect{currentState ->

                    if (currentState.refresh){

                        if (currentState.dailyTraining != null){

                            binding.homeTrainingNotSelectedRoot.visibility = View.GONE
                            binding.homeTrainingSelectedRoot.visibility = View.VISIBLE
                            binding.tvHomeTrainingName.text = currentState.dailyTraining.training!!.name

                            if (currentState.dailyTraining.training.description.isNotBlank()){
                                binding.tvHomeTrainingDescription.visibility = View.VISIBLE
                                binding.tvHomeTrainingDescription.text = currentState.dailyTraining.training.description
                            }else{
                                binding.tvHomeTrainingDescription.visibility = View.GONE
                            }

                            adapter.changeExerciseList(currentState.dailyTraining.training.exercises)
                            adapter.notifyDataSetChanged()

                        }else{

                            binding.homeTrainingNotSelectedRoot.visibility = View.VISIBLE
                            binding.homeTrainingSelectedRoot.visibility = View.GONE

                        }

                        homeViewModel.refreshed()

                    }

                }

            }

        }
    }

    private fun setListeners() {

        binding.fabHomeDiscardTraining.setOnClickListener { homeViewModel.removeDailyTraining() }

        binding.fabHomeStartDailyTraining.setOnClickListener {
            DailyTrainingProgressDialog(homeViewModel.uiState.value.dailyTraining!!.training!!, this).show(parentFragmentManager, "daily_training_progress_dialog")
        }

        binding.fabHomeChangeDailyTraining.setOnClickListener {
            DailyTrainingSelectorDialog(this, homeViewModel.uiState.value.dailyTraining?.training).show(parentFragmentManager, "daily_training_selector_dialog")
        }
        binding.homeTrainingNotSelectedRoot.setOnClickListener {
            DailyTrainingSelectorDialog(this, homeViewModel.uiState.value.dailyTraining?.training).show(parentFragmentManager, "daily_training_selector_dialog")
        }

    }

    override fun onTrainingSelected(dailyTraining: UserInfo.DailyTraining?) {
        homeViewModel.loadDailyTraining(dailyTraining)
    }

    override fun saveDailyTraining() {
        homeViewModel.setDailyTraining()
    }

    override fun saveLog(trainingOfTrainingLog: TrainingOfTrainingLog) {

        homeViewModel.complete(trainingOfTrainingLog)

    }


}