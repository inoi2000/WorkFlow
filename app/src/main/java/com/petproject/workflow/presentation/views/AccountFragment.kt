package com.petproject.workflow.presentation.views

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentAccountBinding
import com.petproject.workflow.presentation.utils.launchAndCollectIn
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

    private val logoutResponse = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.webLogoutComplete()
        } else {
            // логаут отменен
            viewModel.webLogoutComplete()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accessLayout.setOnClickListener {
            val action = AccountFragmentDirections
                .actionAccountFragmentToAccessListFragment()
            findNavController().navigate(action)
        }
        binding.instructionsLayout.setOnClickListener {
            val action = AccountFragmentDirections
                .actionAccountFragmentToInstructionListFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            logoutResponse.launch(it)
        }
        viewModel.navigateToLoginScreen.observe(viewLifecycleOwner) {
            if (it) {
                val intent = AuthActivity.newIntent(requireContext())
                startActivity(intent)
                requireActivity().finish()
            }
        }
        viewModel.employee.observe(viewLifecycleOwner) { employee ->
            if (employee.photoUrl != null) {
                viewModel.requestManager
                    .load(employee.photoUrl)
                    .into(binding.photoImageView)
            } else {
                binding.photoImageView.setImageResource(R.drawable.ic_person)
            }
            viewModel.requestManager
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}