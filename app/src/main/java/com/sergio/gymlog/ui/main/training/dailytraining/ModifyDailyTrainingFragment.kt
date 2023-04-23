package com.sergio.gymlog.ui.main.training.dailytraining

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentModifyDailyTrainingBinding
import com.sergio.gymlog.ui.main.training.dailytraining.adapter.ModifyDailyTrainingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ModifyDailyTrainingFragment : Fragment() {

    private lateinit var binding: FragmentModifyDailyTrainingBinding
    private lateinit var adapter : ModifyDailyTrainingAdapter

    private val mdtViewModel by viewModels<ModifyDailyTrainingViewModel>()

    private var selectionMode : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModifyDailyTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectionMode = false
        mdtViewModel.load()
        initRecyclerView()
        setCollector()
        setListeners()
    }

    private fun initRecyclerView() {
        adapter = ModifyDailyTrainingAdapter(mdtViewModel.uiState.value.trainings, onClickTraining = {position -> onClickTraining(position)}, selectionMode)
        val recycler = binding.rvModDTrainingTrainings
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                mdtViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.pbModDTainingLoading.visibility = View.VISIBLE

                    }

                    if (currentState.trainingSelected){

                        val training = currentState.selectedTraining!!.training!!

                        binding.trainingSelectedLayout.tvTrainingName.text = training.name
                        //binding.trainingSelectedLayout.tvMucularGroups.text =
                        binding.trainingSelectedLayout.tvExercisesNumber.text = getString(R.string.number_of_exercises, training.exercises.size)
                        mdtViewModel.resetStates()

                    }

                    if (currentState.loaded){

                        binding.pbModDTainingLoading.visibility = View.GONE
                        adapter.trainingList = currentState.trainings
                        if (currentState.selectedTraining != null){

                            adapter.selectedTrainingPos = currentState.trainings.indexOfFirst { e -> e == currentState.selectedTraining.training }

                        }
                        adapter.notifyDataSetChanged()



                    }

                }

            }
        }

    }

    private fun setListeners() {
        binding.btnEditDailyTraining.setOnClickListener {

            selectionMode = if (selectionMode){

                binding.btnEditDailyTraining.text = getString(R.string.start_selection_daily_training)
                if (adapter.selectedTrainingPos != -1){
                    mdtViewModel.setTrainingSelectedAsDailyTraining(adapter.selectedTrainingPos)
                }
                false

            }else{

                binding.btnEditDailyTraining.text = getString(R.string.end_selection_daily_training)
                true
            }

            adapter.selectionMode = selectionMode

        }
        binding.tvNewTraining.setOnClickListener {
            val action = ModifyDailyTrainingFragmentDirections.actionGlobalTrainingEditorFragment(emptyArray())
           findNavController().navigate(action)
        }

    }

    private fun onClickTraining(exercisePosition : Int) {

        if (!selectionMode){

            //TODO ir a detalles del ejercicio

        }

    }

}