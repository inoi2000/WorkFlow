package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentAccessListBinding
import com.petproject.workflow.presentation.viewmodels.AccessListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.AccessAdapter
import javax.inject.Inject

class AccessListFragment : Fragment() {
    private var _binding: FragmentAccessListBinding? = null
    private val binding: FragmentAccessListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[AccessListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentAccessListBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setRecyclerView() {
        val adapter = AccessAdapter()
        binding.accessListRecyclerView.itemAnimator = null
        binding.accessListRecyclerView.adapter = adapter
        viewModel.accessList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}