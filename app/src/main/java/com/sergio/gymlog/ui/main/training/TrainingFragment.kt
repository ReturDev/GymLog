package com.sergio.gymlog.ui.main.training

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.sergio.gymlog.util.SpacingItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrainingFragment : Fragment(), DeleteTrainingListener {

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

        adapter = TrainingAdapter(trainingViewModel.uiState.value.trainings, onClickElement = {id -> onClickTraining(id)}, onLongClickTraining = {position -> onLongClickTraining(position)})
        val recycler = binding.rvTrainings
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.addItemDecoration(SpacingItemDecorator(R.dimen.recycler_decoration_bottom_spacing))

    }

    private fun onClickTraining(id : String){

        val action = TrainingFragmentDirections.actionTrainingFragmentToTrainingDetailsFragment(id)
        findNavController().navigate(action)

    }

    private fun onLongClickTraining(trainingPos : Int){

        OnLongClickTrainingDialog(trainingPos,this).show(parentFragmentManager,"on_long_click_training_dialog")

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                trainingViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.trainingContentRoot.visibility = View.GONE
                        binding.pbTrainingLoading.visibility = View.VISIBLE

                    }

                    if (currentState.loaded){

                        binding.trainingContentRoot.visibility = View.VISIBLE
                        binding.pbTrainingLoading.visibility = View.GONE

                        if (currentState.trainings.isEmpty()){

                            binding.tvRecyclerEmpty.visibility = View.VISIBLE

                        }else{

                            binding.tvRecyclerEmpty.visibility = View.GONE
                            adapter.trainingList = currentState.trainings
                            adapter.notifyDataSetChanged()

                        }

                        trainingViewModel.resetStates()

                    }

                    if (currentState.trainingDeletedPosition != -1){

                        adapter.notifyItemRemoved(currentState.trainingDeletedPosition)
                        trainingViewModel.resetStates()

                    }

                }

            }
        }

    }

    private fun setListeners() {
        binding.floatingActionButton.setOnClickListener {
            val action = TrainingFragmentDirections.actionGlobalTrainingEditorFragment(emptyArray())
            findNavController().navigate(action)
        }
    }

    override fun onClickDelete(position: Int) {
        trainingViewModel.deleteTraining(position)
    }

}