package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.databinding.FragmentServiceListBinding
import com.petproject.workflow.domain.entities.AbsenceType

class ServiceListFragment : Fragment() {
    private var _binding: FragmentServiceListBinding? = null
    private val binding: FragmentServiceListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vacationsCardView.setOnClickListener {
            val action = ServiceListFragmentDirections
                .actionServiceListFragmentToAbsenceListFragment(AbsenceType.VACATION)
            findNavController().navigate(action)
        }
        binding.daysOffCardView.setOnClickListener {
            val action = ServiceListFragmentDirections
                .actionServiceListFragmentToAbsenceListFragment(AbsenceType.DAY_OFF)
            findNavController().navigate(action)
        }
        binding.sickLeavesCardView.setOnClickListener {
            val action = ServiceListFragmentDirections
                .actionServiceListFragmentToAbsenceListFragment(AbsenceType.SICK_LEAVE)
            findNavController().navigate(action)
        }
        binding.businessTripsCardView.setOnClickListener {
            val action = ServiceListFragmentDirections
                .actionServiceListFragmentToAbsenceListFragment(AbsenceType.BUSINESS_TRIP)
            findNavController().navigate(action)
        }
    }
}