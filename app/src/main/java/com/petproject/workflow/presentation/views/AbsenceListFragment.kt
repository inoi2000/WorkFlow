package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.petproject.workflow.databinding.FragmentAbsenceListBinding

class AbsenceListFragment : Fragment() {
    private var _binding: FragmentAbsenceListBinding? = null
    private val binding: FragmentAbsenceListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAbsenceListBinding.inflate(inflater, container, false)
        return binding.root
    }
}