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
import com.petproject.workflow.databinding.FragmentExecutorTaskInfoBinding
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
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
        binding.commentsCardView.setOnClickListener {
            val action = ExecutorTaskInfoFragmentDirections
                .actionExecutingTaskInfoFragmentToTaskCommentListFragment(args.taskId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}