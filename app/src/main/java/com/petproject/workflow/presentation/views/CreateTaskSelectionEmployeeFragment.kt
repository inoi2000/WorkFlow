package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskSelectionEmployeeBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeAdapter
import javax.inject.Inject

class CreateTaskSelectionEmployeeFragment : Fragment() {
    private var _binding: FragmentCreateTaskSelectionEmployeeBinding? = null
    private val binding: FragmentCreateTaskSelectionEmployeeBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[CreateTaskViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateTaskSelectionEmployeeBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnClickListener {
            val action = CreateTaskSelectionEmployeeFragmentDirections
                .actionCreateTaskSelectionEmployeeFragmentToCreateTaskAddDetailsFragment()
            findNavController().navigate(action)
        }
    }

    private fun setRecyclerView() {
        val adapter = EmployeeAdapter();

        binding.employeeListRecyclerView.itemAnimator = null
        binding.employeeListRecyclerView.adapter = adapter
        viewModel.employeeList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}