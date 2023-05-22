package com.sergio.gymlog.ui.main.exercise.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentUserExercisesListBinding
import com.sergio.gymlog.ui.main.exercise.ExerciseDialogListener
import com.sergio.gymlog.ui.main.exercise.detail.ExerciseDetailDialog
import com.sergio.gymlog.ui.main.exercise.dialog.ExerciseClickedDialog
import com.sergio.gymlog.ui.main.exercise.user.adapter.UserExercisesListAdapter
import com.sergio.gymlog.util.SpacingItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserExercisesListFragment : Fragment(), ExerciseDialogListener {

    private val userExercisesListViewModel by viewModels<UserExercisesListViewModel>()

    private lateinit var binding : FragmentUserExercisesListBinding
    private lateinit var adapter : UserExercisesListAdapter

    private lateinit var exerciseClickedDialog : ExerciseClickedDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserExercisesListBinding.inflate(inflater,container,false)
        val bottomMenu = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomMenu.visibility = View.VISIBLE

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setCollector()
        setListeners()

        userExercisesListViewModel.loadUserExercises()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){

                userExercisesListViewModel.uiState.collect{currentState ->

                    if (currentState.loading) {

                        binding.pbUserExercisesLoading.visibility = View.VISIBLE
                        binding.userExerciseContentRoot.visibility = View.GONE

                    }

                    if (currentState.loaded){

                        binding.pbUserExercisesLoading.visibility = View.GONE
                        binding.userExerciseContentRoot.visibility = View.VISIBLE

                        if (currentState.userExercises.isEmpty()){
                            binding.tvUserExercisesEmptyList.visibility = View.VISIBLE
                        }else{
                            binding.tvUserExercisesEmptyList.visibility = View.GONE
                        }

                        adapter.userExercises.addAll(currentState.userExercises)
                        adapter.notifyDataSetChanged()
                        userExercisesListViewModel.resetStates()

                    }

                    if (currentState.exerciseDeletedPos != -1){

                        adapter.userExercises.removeAt(currentState.exerciseDeletedPos)
                        adapter.notifyItemRemoved(currentState.exerciseDeletedPos)
                        userExercisesListViewModel.resetStates()

                    }

                }

            }
        }

    }

    private fun setListeners() {

        binding.fbUserExercisesListNewExercise.setOnClickListener {

            val action = UserExercisesListFragmentDirections.actionGlobalExerciseCreatorFragment()
            findNavController().navigate(action)

        }

    }

    private fun initRecyclerView() {

        adapter = UserExercisesListAdapter(mutableListOf()) { pos -> onClickExercise(pos) }
        val recycler = binding.rvUserExercisesList
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.addItemDecoration(SpacingItemDecorator(200))

    }

    private fun onClickExercise(exercisePos : Int){

        exerciseClickedDialog =  ExerciseClickedDialog(this,exercisePos)

        exerciseClickedDialog.show(parentFragmentManager,"exercise_clicked_dialog")

    }

    override fun onClickInformation(exercisePos : Int) {

        val detailDialog = ExerciseDetailDialog(adapter.userExercises[exercisePos])
        detailDialog.show(parentFragmentManager, "exercise_details")

    }

    override fun onClickDelete(position: Int) {

        userExercisesListViewModel.deleteUserExercise(position)

    }

}