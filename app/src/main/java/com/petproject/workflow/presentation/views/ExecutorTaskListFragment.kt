package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentExecutorTaskListBinding
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
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
            TaskAdapter.EXECUTOR_MODE,
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
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
        val greyColor = ContextCompat.getColor(requireContext(), R.color.grey_for_text)

        binding.overdueTasksCounterTextView.setTextColor(greyColor)
        binding.overdueTasksCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        binding.urgentTasksCounterTextView.setTextColor(greyColor)
        binding.urgentTasksCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        binding.onApprovalTasksCounterTextView.setTextColor(greyColor)
        binding.onApprovalTasksCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        binding.finishedTasksCounterTextView.setTextColor(greyColor)
        binding.finishedTasksCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        when (cardView) {
            binding.overdueTasksCardView -> {
                binding.overdueTasksCounterTextView.setTextColor(whiteColor)
                binding.overdueTasksCounterTextView.setBackgroundResource(R.drawable.circle_yellow)
            }

            binding.urgentTasksCardView -> {
                binding.urgentTasksCounterTextView.setTextColor(whiteColor)
                binding.urgentTasksCounterTextView.setBackgroundResource(R.drawable.circle_red)
            }

            binding.onApprovalTasksCardView -> {
                binding.onApprovalTasksCounterTextView.setTextColor(whiteColor)
                binding.onApprovalTasksCounterTextView.setBackgroundResource(R.drawable.circle_blue)
            }

            binding.finishedTasksCardView -> {
                binding.finishedTasksCounterTextView.setTextColor(whiteColor)
                binding.finishedTasksCounterTextView.setBackgroundResource(R.drawable.circle_green)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}