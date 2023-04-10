package com.sergio.gymlog.ui.main.training

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
import com.sergio.gymlog.databinding.FragmentTrainingBinding
import com.sergio.gymlog.ui.main.training.adapter.TrainingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrainingFragment : Fragment() {

    private lateinit var binding: FragmentTrainingBinding
    private val trainingViewModel by viewModels<TrainingViewModel>()
    private lateinit var adapter : TrainingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trainingViewModel.loadTrainings()
        initRecyclerView()
        setCollector()
        setListeners()

    }

    private fun initRecyclerView() {

        adapter = TrainingAdapter(trainingViewModel.uiState.value.trainings, onClickElement = {id -> onClickTraining(id)})
        val recycler = binding.rvTrainings
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun onClickTraining(id : String){

        val action = TrainingFragmentDirections.actionTrainingFragmentToTrainingDetailsFragment(id)
        findNavController().navigate(action)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                trainingViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.tvRecyclerEmpty.visibility = View.VISIBLE
                        binding.etSearchTraining.isEnabled = false

                    }

                    if (currentState.loaded){

                        adapter.trainingList = currentState.trainings
                        adapter.notifyDataSetChanged()
                        binding.tvRecyclerEmpty.visibility = View.GONE
                        binding.etSearchTraining.isEnabled = true
                        trainingViewModel.resetLoaded()

                    }

                }

            }
        }

    }

    private fun setListeners() {
        binding.btnCreateNewTraining.setOnClickListener {
            findNavController().navigate(R.id.action_global_trainingEditorFragment)
        }
    }

}