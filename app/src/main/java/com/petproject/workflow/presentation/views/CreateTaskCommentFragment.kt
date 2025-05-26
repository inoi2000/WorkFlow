package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskCommentBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskCommentViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.TaskCommentListFragment.Companion.MODE_FROM_EXECUTOR
import com.petproject.workflow.presentation.views.TaskCommentListFragment.Companion.MODE_FROM_INSPECTOR
import javax.inject.Inject

class CreateTaskCommentFragment : Fragment() {

    private var _binding: FragmentCreateTaskCommentBinding? = null
    private val binding: FragmentCreateTaskCommentBinding get() = _binding!!

    private val args by navArgs<CreateTaskCommentFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[CreateTaskCommentViewModel::class]
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
        _binding = FragmentCreateTaskCommentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.commentsCounterTextView.text = args.commentsCount
        binding.detailsButton.setOnClickListener {
            val action = when (args.modeForm) {
                MODE_FROM_INSPECTOR -> {
                    CreateTaskCommentFragmentDirections
                        .actionCreateTaskCommentFragmentToInspectorTaskInfoFragment(args.taskId)
                }

                MODE_FROM_EXECUTOR -> {
                    CreateTaskCommentFragmentDirections
                        .actionCreateTaskCommentFragmentToExecutingTaskInfoFragment(args.taskId)
                }

                else -> {
                    throw RuntimeException()
                }
            }
            findNavController().navigate(action)
        }
        binding.createNewCommentButton.setOnClickListener {
            val commentText =binding.etText.text.toString()
            viewModel.createComment(commentText)
        }
    }

    private fun observeViewModel() {
        viewModel.isCommentCreated.observe(viewLifecycleOwner) {
            if (it) {
                val action = CreateTaskCommentFragmentDirections
                    .actionCreateTaskCommentFragmentToTaskCommentListFragment(
                        args.taskId,
                        args.modeForm
                    )
                findNavController().navigate(action)
            }
        }
    }
}