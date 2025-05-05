package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentAnnouncementListBinding
import com.petproject.workflow.presentation.viewmodels.AnnouncementViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.AnnouncementAdapter
import javax.inject.Inject

class AnnouncementListFragment : Fragment() {
    private var _binding: FragmentAnnouncementListBinding? = null
    private val binding: FragmentAnnouncementListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[AnnouncementViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentAnnouncementListBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setRecyclerView() {
        val adapter = AnnouncementAdapter()
        binding.announcementListRecyclerView.itemAnimator = null
        binding.announcementListRecyclerView.adapter = adapter
        viewModel.announcementList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}