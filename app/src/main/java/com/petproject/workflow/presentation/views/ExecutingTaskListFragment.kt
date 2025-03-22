package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentExecutingTaskListBinding
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.ExecutingTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import javax.inject.Inject

class ExecutingTaskListFragment : Fragment() {
    private var _binding: FragmentExecutingTaskListBinding? = null
    private val binding: FragmentExecutingTaskListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[ExecutingTaskListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentExecutingTaskListBinding.inflate(inflater, container, false)
        setRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setRecyclerView() {
        val adapter = TaskAdapter {
            val action = ExecutingTaskListFragmentDirections
                .actionExecutingTaskListFragmentToExecutingTaskInfoFragment(it.id)
            findNavController().navigate(action)
        }
        binding.tasksListRecyclerView.itemAnimator = null
        binding.tasksListRecyclerView.adapter = adapter
        viewModel.executingTasksList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}