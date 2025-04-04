package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentExecutorTaskListBinding
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import com.petproject.workflow.presentation.views.adapters.TaskInfoViewHolder
import javax.inject.Inject

class ExecutorTaskListFragment : Fragment() {
    private var _binding: FragmentExecutorTaskListBinding? = null
    private val binding: FragmentExecutorTaskListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[ExecutorTaskListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentExecutorTaskListBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.overdueTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.FAILED)
            choseCardView(binding.overdueTasksCardView)
        }
        binding.urgentTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByPriority(TaskPriority.URGENT)
            choseCardView(binding.urgentTasksCardView)
        }
        binding.onApprovalTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.ON_APPROVAL)
            choseCardView(binding.onApprovalTasksCardView)
        }
        binding.finishedTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.COMPLETED)
            choseCardView(binding.finishedTasksCardView)
        }

    }

    private fun setRecyclerView() {
        val adapter = TaskAdapter(
            TaskInfoViewHolder.EXECUTOR_MODE,
            { taskId ->
                val action = ExecutorTaskListFragmentDirections
                    .actionExecutingTaskListFragmentToExecutingTaskInfoFragment(taskId)
                findNavController().navigate(action)
            },
            { taskId ->
                val action = ExecutorTaskListFragmentDirections
                    .actionExecutingTaskListFragmentToTaskCommentListFragment(taskId)
                findNavController().navigate(action)
            })
        binding.tasksListRecyclerView.itemAnimator = null
        binding.tasksListRecyclerView.adapter = adapter
        viewModel.filteredTaskList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun choseCardView(cardView: CardView) {
        binding.overdueTasksSelector.visibility = View.GONE
        binding.urgentTasksSelector.visibility = View.GONE
        binding.onApprovalTasksSelector.visibility = View.GONE
        binding.finishedTasksSelector.visibility = View.GONE

        when (cardView) {
            binding.overdueTasksCardView -> {
                binding.overdueTasksSelector.visibility = View.VISIBLE
            }

            binding.urgentTasksCardView -> {
                binding.urgentTasksSelector.visibility = View.VISIBLE
            }

            binding.onApprovalTasksCardView -> {
                binding.onApprovalTasksSelector.visibility = View.VISIBLE
            }

            binding.finishedTasksCardView -> {
                binding.finishedTasksSelector.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}