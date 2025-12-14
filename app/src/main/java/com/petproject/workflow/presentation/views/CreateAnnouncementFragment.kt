package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateAnnouncementBinding
import com.petproject.workflow.presentation.viewmodels.CreateAnnouncementViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class CreateAnnouncementFragment : Fragment() {
    private var _binding: FragmentCreateAnnouncementBinding? = null
    private val binding: FragmentCreateAnnouncementBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[CreateAnnouncementViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateAnnouncementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}