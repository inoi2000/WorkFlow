package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentInspectorTaskListBinding
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.InspectorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import com.petproject.workflow.presentation.views.adapters.TaskInfoViewHolder
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
            viewModel.filteredTaskListByDefault()
            choseCardView(binding.allTasksCardView)
        }

        binding.onApprovalTasksCardView.callOnClick()
    }

    private fun setRecyclerView() {
        val adapter = TaskAdapter(
            TaskInfoViewHolder.INSPECTOR_MODE,
            { taskId ->
                val action = InspectorTaskListFragmentDirections
                    .actionInspectorTaskListFragmentToInspectorTaskInfoFragment(taskId)
                findNavController().navigate(action)
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
        binding.onApprovalTasksSelector.visibility = View.GONE
        binding.allTasksSelector.visibility = View.GONE

        when (cardView) {
            binding.onApprovalTasksCardView -> {
                binding.onApprovalTasksSelector.visibility = View.VISIBLE
            }
            binding.allTasksCardView -> {
                binding.allTasksSelector.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}