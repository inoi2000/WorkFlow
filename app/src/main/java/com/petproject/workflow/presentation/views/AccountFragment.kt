package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentAccountBinding
import com.petproject.workflow.presentation.viewmodels.AccountViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding: FragmentAccountBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[AccountViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.btnLogout.setOnClickListener {
            viewModel.signOut()
        }
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.navigateToLoginScreen.observe(viewLifecycleOwner) {
            if (it) {
                val intent = LoginActivity.newIntent(requireContext())
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}