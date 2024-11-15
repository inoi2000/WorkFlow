package com.petproject.workflow.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.R
import com.petproject.workflow.databinding.FragmentAccountBinding
import com.petproject.workflow.databinding.FragmentLoginBinding

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding: FragmentAccountBinding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore)[AccountViewModel::class]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.btnLogout.setOnClickListener {
            viewModel.signOut()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}