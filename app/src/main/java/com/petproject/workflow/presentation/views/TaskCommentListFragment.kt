package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentTaskCommentListBinding
import com.petproject.workflow.presentation.viewmodels.TaskCommentListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.CommentAdapter
import javax.inject.Inject

class TaskCommentListFragment : Fragment() {
    private var _binding: FragmentTaskCommentListBinding? = null
    private val binding: FragmentTaskCommentListBinding get() = _binding!!

    private val args by navArgs<TaskCommentListFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[TaskCommentListViewModel::class]
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
        _binding = FragmentTaskCommentListBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        binding.detailsButton.setOnClickListener {
            val action = TaskCommentListFragmentDirections
                .actionTaskCommentListFragmentToExecutingTaskInfoFragment(args.taskId)
            findNavController().navigate(action)
        }
    }

    private fun setRecyclerView() {
        val adapter = CommentAdapter()
        binding.commentsListRecyclerView.itemAnimator = null
        binding.commentsListRecyclerView.adapter = adapter
        viewModel.comments.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}