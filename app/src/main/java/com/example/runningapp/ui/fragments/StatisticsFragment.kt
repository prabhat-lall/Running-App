package com.example.runningapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.runningapp.R
import com.example.runningapp.databinding.FragmentRunBinding
import com.example.runningapp.databinding.FragmentStatisticsBinding
import com.example.runningapp.ui.viewmodels.MainViewModel
import com.example.runningapp.ui.viewmodels.StatisticsViewModel
import com.example.runningapp.utility.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val viewModel : StatisticsViewModel by viewModels()

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner , Observer {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopWatch(it)
                binding.tvTotalTime.text = totalTimeRun
            }
        })

        viewModel.totalDistance.observe(viewLifecycleOwner , Observer {
            it?.let {
                val km = it /1000f
                val totalDistance = round(km * 10f) /10f
                val totalDistanceString = "${totalDistance}km"

                binding.tvTotalDistance.text = totalDistanceString
            }
        })
    }
}