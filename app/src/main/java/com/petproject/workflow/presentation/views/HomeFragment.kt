package com.petproject.workflow.presentation.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentHomeBinding
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[HomeViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication)
            .component
            .activityComponentFactory()
            .create((requireActivity() as MainActivity).employeeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
        setNavigation()
    }

    private fun setNavigation() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Скрываем клавиатуру
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                // Получаем текст для передачи в следующий фрагмент
                val searchQuery = binding.etSearch.text.toString().trim()
                // Переходим к другому фрагменту
                if(searchQuery.isNotBlank()){
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToSearchFragment(searchQuery)
                    findNavController().navigate(action)
                }
                return@setOnEditorActionListener true
            }
            false
        }
        binding.vacationsCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToAbsenceListFragment(AbsenceType.VACATION)
            findNavController().navigate(action)
        }
        binding.sickLeavesCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToAbsenceListFragment(AbsenceType.SICK_LEAVE)
            findNavController().navigate(action)
        }
        binding.daysOffCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToAbsenceListFragment(AbsenceType.DAY_OFF)
            findNavController().navigate(action)
        }

        binding.onApprovalTasksCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToInspectorTaskListFragment()
            findNavController().navigate(action)
        }
        binding.executorTasksCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToExecutingTaskListFragment()
            findNavController().navigate(action)
        }
        binding.journeysCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToJourneyListFragment()
            findNavController().navigate(action)
        }
        binding.statementsCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToStatementListFragment()
            findNavController().navigate(action)
        }
        binding.fuellingsCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToFuellingListFragment()
            findNavController().navigate(action)
        }

        binding.othersCardView.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToServiceListFragment()
            findNavController().navigate(action)
        }

        binding.taskManagement.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToCreateTaskSelectionEmployeeFragment()
            findNavController().navigate(action)
        }
        binding.taskExecution.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToExecutingTaskListFragment()
            findNavController().navigate(action)
        }
        binding.announcementCreation.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToCreateAnnouncementFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.navigateToLoginScreen.observe(viewLifecycleOwner) {
            if (it) {
                val intent = AuthActivity.newIntent(requireContext())
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