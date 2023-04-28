package com.sergio.gymlog.ui.main.exercise.user

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
import com.sergio.gymlog.databinding.FragmentUserExercisesListBinding
import com.sergio.gymlog.ui.main.exercise.user.adapter.UserExercisesListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserExercisesListFragment : Fragment() {

    private val userExercisesListViewModel by viewModels<UserExercisesListViewModel>()

    private lateinit var binding : FragmentUserExercisesListBinding
    private lateinit var adapter : UserExercisesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserExercisesListBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setCollector()
        setListeners()

        userExercisesListViewModel.loadUserExercises()

    }

    private fun setCollector() {

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){

                userExercisesListViewModel.uiState.collect{currentState ->

                    if (currentState.loaded){

                        adapter.userExercises.addAll(currentState.userExercises)
                        userExercisesListViewModel.resetStates()

                    }

                }

            }
        }

    }

    private fun setListeners() {

    }

    private fun initRecyclerView() {

        adapter = UserExercisesListAdapter(mutableListOf())
        val recycler = binding.rvUserExercisesList
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

}