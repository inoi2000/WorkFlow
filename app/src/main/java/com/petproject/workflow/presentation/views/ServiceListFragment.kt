package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentServiceListBinding
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.presentation.viewmodels.ServiceListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ServiceListFragment : Fragment() {
    private var _binding: FragmentServiceListBinding? = null
    private val binding: FragmentServiceListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[ServiceListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentServiceListBinding.inflate(inflater, container, false)
        observeViewModel()
        setOnClickListeners()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.navigateToAbsenceListScreen.observe(viewLifecycleOwner) {
            it?.let {
                val action = ServiceListFragmentDirections
                    .actionServiceListFragmentToAbsenceListFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.vacationsCardView.setOnClickListener {
            viewModel.navigateToAbsenceListScreen(AbsenceType.VACATION)
            viewModel.onAbsenceScreenNavigated()
        }
        binding.daysOffCardView.setOnClickListener {
            viewModel.navigateToAbsenceListScreen(AbsenceType.DAY_OFF)
            viewModel.onAbsenceScreenNavigated()
        }
        binding.sickLeavesCardView.setOnClickListener {
            viewModel.navigateToAbsenceListScreen(AbsenceType.SICK_LEAVE)
            viewModel.onAbsenceScreenNavigated()
        }
        binding.businessTripsCardView.setOnClickListener {
            viewModel.navigateToAbsenceListScreen(AbsenceType.BUSINESS_TRIP)
            viewModel.onAbsenceScreenNavigated()
        }
        binding.carsCardView.setOnClickListener {
            val action = ServiceListFragmentDirections
                .actionServiceListFragmentToCarListFragment()
            findNavController().navigate(action)
        }
        binding.trailersCardView.setOnClickListener {
            val action = ServiceListFragmentDirections
                .actionServiceListFragmentToTrailerListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}