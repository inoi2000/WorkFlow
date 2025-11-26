package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentExecutorTaskInfoBinding
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeInfoViewHolder
import javax.inject.Inject

class ExecutorTaskInfoFragment : Fragment() {
    private var _binding: FragmentExecutorTaskInfoBinding? = null
    private val binding: FragmentExecutorTaskInfoBinding get() = _binding!!

    private val args by navArgs<ExecutorTaskInfoFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[ExecutorTaskInfoViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication)
            .component
            .activityComponentFactory()
            .create(args.taskId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentExecutorTaskInfoBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.commentsCardView.setOnClickListener {
            val action = ExecutorTaskInfoFragmentDirections
                .actionExecutingTaskInfoFragmentToTaskCommentListFragment(
                    args.taskId,
                    TaskCommentListFragment.MODE_FROM_EXECUTOR
                )
            findNavController().navigate(action)
        }
        binding.acceptTaskButton.setOnClickListener {
            viewModel.acceptTask()
        }
        binding.submitTaskButton.setOnClickListener {
            viewModel.submitTask()
        }
    }

    private fun observeViewModel() {
        viewModel.executingTask.observe(viewLifecycleOwner) { task ->
            task.inspector?.let { inspector ->
                val employeeInfoViewHolder = EmployeeInfoViewHolder(
                    binding.taskInspector,
                    viewModel.requestManager)
                employeeInfoViewHolder.bind(inspector) {}
            }
            binding.acceptTaskButton.visibility =
                if (task.status == TaskStatus.NEW) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            binding.submitTaskButton.visibility =
                if (task.status == TaskStatus.IN_PROGRESS || task.status == TaskStatus.NOT_APPROVAL) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
        viewModel.successMessage.observe(viewLifecycleOwner) { successMessage ->
            successMessage?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}