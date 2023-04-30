package com.sergio.gymlog.ui.main.user

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.user.UserInfo
import com.sergio.gymlog.data.repository.access.LoginRepository
import com.sergio.gymlog.data.repository.user.UserDataRepository
import com.sergio.gymlog.util.extension.userDataChangeDialog
import com.sergio.gymlog.databinding.DialogChangeUserDataBinding
import com.sergio.gymlog.databinding.DialogChangeUserNameBinding
import com.sergio.gymlog.databinding.FragmentUserBinding
import com.sergio.gymlog.util.InputFiltersProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding : FragmentUserBinding
    private val userViewModel by viewModels<UserViewModel>()

    lateinit var loginRepository : LoginRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.loadUserData()
        setCollector()
        setListeners()
    }

    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                userViewModel.uiState.collect{currentState ->

                    if (currentState.userInfoLoaded){

                        binding.tvUserName.text = currentState.userInfo!!.username
                        if (currentState.userInfo.photo.isNotBlank()){
                            //TODO Introducir foto usuario.
                        }

                        binding.tvUserSetsQuantity.text = currentState.userInfo.sets.toString()
                        binding.tvUserRepetitionsQuantity.text = currentState.userInfo.repetitions.toString()
                        binding.tvUserWeightQuantity.text = currentState.userInfo.weight.toString()

                        userViewModel.uiUpdated()

                    }

                }

            }

        }

    }

    private fun setListeners() {

        binding.btnUserExercises.setOnClickListener {

            findNavController().navigate(R.id.action_fragment_user_to_userExercisesListFragment)

        }

        binding.userSetsRoot.setOnClickListener{

            AlertDialog.Builder(context).userDataChangeDialog(
                titleResource = R.string.exercise_sets,
                currentValue = binding.tvUserSetsQuantity.text.toString(),
                units = false,
                acceptAction = { value ->
                    userViewModel.changeUserInfo(UserInfo.SETS_TAG, value)
                },
                context = requireContext()
            )

        }

        binding.userRepetitionsRoot.setOnClickListener {

            AlertDialog.Builder(context).userDataChangeDialog(
                titleResource = R.string.repetitions,
                currentValue = binding.tvUserRepetitionsQuantity.text.toString(),
                units = false,
                acceptAction = { value ->
                    userViewModel.changeUserInfo(UserInfo.REPETITIONS_TAG, value)
                },
                context = requireContext()
            )

        }

        binding.userWeightRoot.setOnClickListener {

            AlertDialog.Builder(context).userDataChangeDialog(
                titleResource = R.string.user_weight,
                currentValue = binding.tvUserWeightQuantity.text.toString(),
                units = true,
                acceptAction = { value ->
                    userViewModel.changeUserInfo(UserInfo.WEIGHT_TAG, value)
                },
                context = requireContext()
            )

        }


        binding.ivUserEditName.setOnClickListener {

            val dialogBinding = DialogChangeUserNameBinding.inflate(LayoutInflater.from(requireContext()))

            dialogBinding.etChangeUsernameValue.filters = arrayOf(InputFiltersProvider.usernameFilter())
            dialogBinding.etChangeUsernameValue.text = SpannableStringBuilder(binding.tvUserName.text)
            dialogBinding.ivChangeUsernameAccept.isEnabled = binding.tvUserName.text.isNotBlank()
            dialogBinding.etChangeUsernameValue.addTextChangedListener {

                it?.let {

                    dialogBinding.ivChangeUsernameAccept.isEnabled = it.isNotBlank()

                }

            }

            val dialog = AlertDialog.Builder(context)
                .setTitle(getString(R.string.user_name_label))
                .setView(dialogBinding.root)
                .setCancelable(true)
                .create()

            dialogBinding.ivChangeUsernameAccept.setOnClickListener {

                userViewModel.changeUserInfo(UserInfo.USERNAME_TAG, dialogBinding.etChangeUsernameValue.text.toString())
                dialog.dismiss()

            }

            dialogBinding.ivChangeUsernameCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()


        }

    }


}