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
import com.petproject.workflow.databinding.FragmentInspectorTaskInfoBinding
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.InspectorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeInfoViewHolder
import javax.inject.Inject

class InspectorTaskInfoFragment : Fragment() {
    private var _binding: FragmentInspectorTaskInfoBinding? = null
    private val binding: FragmentInspectorTaskInfoBinding get() = _binding!!

    private val args by navArgs<InspectorTaskInfoFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[InspectorTaskInfoViewModel::class]
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
        _binding = FragmentInspectorTaskInfoBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.commentsCardView.setOnClickListener {
            val action = InspectorTaskInfoFragmentDirections
                .actionInspectorTaskInfoFragmentToTaskCommentListFragment(
                    args.taskId,
                    TaskCommentListFragment.MODE_FROM_INSPECTOR
                )
            findNavController().navigate(action)
        }
        binding.approvalTaskButton.setOnClickListener {
            viewModel.approvalTask()
        }
        binding.rejectTaskButton.setOnClickListener {
            viewModel.rejectTask()
        }
        binding.cancelTaskButton.setOnClickListener {
            viewModel.cancelTask()
        }
    }

    private fun observeViewModel() {
        viewModel.inspectorTask.observe(viewLifecycleOwner) { task ->
            task.executor?.let { executor ->
                val employeeInfoViewHolder = EmployeeInfoViewHolder(
                    binding.taskExecutor,
                    viewModel.requestManager)
                employeeInfoViewHolder.bind(executor) {}
            }

            binding.approvalTaskButton.visibility =
                if (task.status == TaskStatus.ON_APPROVAL) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            binding.rejectTaskButton.visibility =
                if (task.status == TaskStatus.ON_APPROVAL) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            binding.cancelTaskButton.visibility =
                if (task.status != TaskStatus.COMPLETED && task.status != TaskStatus.CANCELED) {
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