package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentInspectorTaskListBinding
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.InspectorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import javax.inject.Inject

class InspectorTaskListFragment : Fragment() {
    private var _binding: FragmentInspectorTaskListBinding? = null
    private val binding: FragmentInspectorTaskListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[InspectorTaskListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentInspectorTaskListBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onApprovalTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.ON_APPROVAL)
            choseCardView(binding.onApprovalTasksCardView)
        }
        binding.allTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.COMPLETED)
            choseCardView(binding.allTasksCardView)
        }

    }

    private fun setRecyclerView() {
        val adapter = TaskAdapter(
            TaskAdapter.INSPECTOR_MODE,
            { taskId ->
//                val action = ExecutorTaskListFragmentDirections
//                    .actionExecutingTaskListFragmentToExecutingTaskInfoFragment(taskId)
//                findNavController().navigate(action)
            },
            { taskId ->
//                val action = ExecutorTaskListFragmentDirections
//                    .actionExecutingTaskListFragmentToTaskCommentListFragment(taskId)
//                findNavController().navigate(action)
            })
        binding.tasksListRecyclerView.itemAnimator = null
        binding.tasksListRecyclerView.adapter = adapter
        viewModel.filteredTaskList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun choseCardView(cardView: CardView) {
        binding.onApprovalTasksCounterTextView.setTextColor(resources.getColor(R.color.grey_for_text))
        binding.onApprovalTasksCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        binding.allTasksCounterTextView.setTextColor(resources.getColor(R.color.grey_for_text))
        binding.allTasksCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        when (cardView) {
            binding.onApprovalTasksCardView -> {
                binding.onApprovalTasksCounterTextView.setTextColor(resources.getColor(R.color.white))
                binding.onApprovalTasksCounterTextView.setBackgroundResource(R.drawable.circle_blue)
            }
            binding.allTasksCardView -> {
                binding.allTasksCounterTextView.setTextColor(resources.getColor(R.color.white))
                binding.allTasksCounterTextView.setBackgroundResource(R.drawable.circle_green)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}