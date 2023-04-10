package com.sergio.gymlog.ui.main.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentUserBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

    private lateinit var binding : FragmentUserBinding
    private val userViewModel by viewModels<UserViewModel>()

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
        setCollector()
        setListeners()
    }

    private fun setListeners() {
        TODO("Not yet implemented")
    }

    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                userViewModel.uiState.collect{currentState ->



                }

            }
        }

    }

}