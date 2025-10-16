package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentJourneyListBinding
import com.petproject.workflow.presentation.viewmodels.JourneyListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.JourneyAdapter
import javax.inject.Inject

class JourneyListFragment : Fragment() {
    private var _binding: FragmentJourneyListBinding? = null
    private val binding: FragmentJourneyListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[JourneyListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentJourneyListBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setRecyclerView() {
        val adapter = JourneyAdapter {
            //TODO
        }
        binding.journeyListRecyclerView.itemAnimator = null
        binding.journeyListRecyclerView.adapter = adapter
        viewModel.journeyList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}