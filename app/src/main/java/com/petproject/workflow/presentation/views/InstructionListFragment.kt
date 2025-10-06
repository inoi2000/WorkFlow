package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentInstructionListBinding
import com.petproject.workflow.presentation.viewmodels.InstructionListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.InstructionAdapter
import javax.inject.Inject

class InstructionListFragment : Fragment() {
    private var _binding: FragmentInstructionListBinding? = null
    private val binding: FragmentInstructionListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[InstructionListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentInstructionListBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setRecyclerView() {
        val adapter = InstructionAdapter()
        binding.instructionListRecyclerView.itemAnimator = null
        binding.instructionListRecyclerView.adapter = adapter
        viewModel.instructionList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}