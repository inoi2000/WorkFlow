package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskDoneBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskDoneViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTaskDoneFragment : Fragment() {
    private var _binding: FragmentCreateTaskDoneBinding? = null
    private val binding: FragmentCreateTaskDoneBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args by navArgs<CreateTaskDoneFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[CreateTaskDoneViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateTaskDoneBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.createTask(args.task)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}