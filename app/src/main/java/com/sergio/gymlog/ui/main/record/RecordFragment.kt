package com.sergio.gymlog.ui.main.record

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.sergio.gymlog.R
import com.sergio.gymlog.databinding.FragmentRecordBinding
import com.sergio.gymlog.ui.main.record.adapter.RecordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class RecordFragment : Fragment() {

    private lateinit var adapter : RecordAdapter
    private lateinit var binding : FragmentRecordBinding
    private val recordViewModel by viewModels<RecordViewModel>()

    private var dateSelected : Calendar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecordBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setCalendarConfig()
        setCollector()
        setListeners()

    }

    private fun initRecyclerView() {
        adapter = RecordAdapter(emptyList()) { position -> onClickTrainingLog(position) }
        val recycler = binding.rvRecordList
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setCollector() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                recordViewModel.uiState.collect{currentState ->

                    if (currentState.loading){

                        binding.pgRecordLoading.visibility = View.VISIBLE
                        binding.recordContentRoot.visibility = View.GONE

                    }

                    if (currentState.loaded){

                        binding.pgRecordLoading.visibility = View.GONE
                        binding.recordContentRoot.visibility = View.VISIBLE
                        recordViewModel.resetState()

                    }

                    if (currentState.refresh){

                        adapter.trainingLogs = currentState.trainingLogs
                        adapter.notifyDataSetChanged()
                        recordViewModel.resetState()

                    }

                }

            }
        }

    }

    private fun setListeners() {

        binding.calendarRecord.setOnDateChangeListener { view, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance().apply {
                set(year,month,dayOfMonth)
                clear(Calendar.HOUR_OF_DAY)
                clear(Calendar.MINUTE)
                clear(Calendar.SECOND)
                clear(Calendar.MILLISECOND)
            }

            view.date = calendar.timeInMillis

        }


        binding.btnRecordShowCalendar.setOnClickListener {
            if (binding.calendarRecord.visibility == View.VISIBLE){

                binding.calendarRecord.visibility = View.GONE
                recordViewModel.getLog(null)

            }else{

                binding.calendarRecord.visibility = View.VISIBLE
                recordViewModel.getLog(Timestamp(Date(binding.calendarRecord.date)))

            }
        }


    }

    private fun onClickTrainingLog(position : Int){

    }

    private fun setCalendarConfig(){

        binding.calendarRecord.visibility = View.GONE
        binding.calendarRecord.maxDate = Date().time

    }


}