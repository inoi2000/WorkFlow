package com.petproject.workflow.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskSelectionEmployeeBinding
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.presentation.viewmodels.CreateTaskSelectionEmployeeViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeAdapter
import javax.inject.Inject

class CreateTaskSelectionEmployeeFragment : Fragment() {
    private var _binding: FragmentCreateTaskSelectionEmployeeBinding? = null
    private val binding: FragmentCreateTaskSelectionEmployeeBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CreateTaskSelectionEmployeeViewModel

    private val employeeAdapter by lazy {
        EmployeeAdapter(viewModel.requestManager) { employee ->
            navigateToDetails(employee)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as WorkFlowApplication).component.inject(this)
        _binding = FragmentCreateTaskSelectionEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[CreateTaskSelectionEmployeeViewModel::class.java]
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupViews() {
        setupRecyclerView()
        setupSwipeRefresh()
        setupSearch()
        setupRetryButton()
    }

    private fun setupRecyclerView() {
        with(binding.employeeListRecyclerView) {
            adapter = employeeAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.blue)
        )
    }

    private fun setupSearch() {
        binding.searchEditText.doAfterTextChanged { text ->
//            viewModel.filterEmployees(text.toString())
        }
    }

    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun setupObservers() {
        viewModel.employeeList.observe(viewLifecycleOwner) { employees ->
            employeeAdapter.submitList(employees)
            showContentState()
        }

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
            if (isLoading) showLoadingState()
        }

        viewModel.errorState.observe(viewLifecycleOwner) { error ->
            error?.let { showErrorState(it) }
        }
    }

    private fun showLoadingState() {
        binding.employeeListRecyclerView.visibility = View.GONE
        binding.loadingState.visibility = View.VISIBLE
        binding.emptyState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
    }

    private fun showContentState() {
        val isEmpty = employeeAdapter.itemCount == 0
        binding.employeeListRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.loadingState.visibility = View.GONE
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.errorState.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
    }

    private fun showErrorState(error: String) {
        binding.employeeListRecyclerView.visibility = View.GONE
        binding.loadingState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.errorState.visibility = View.VISIBLE
        binding.retryButton.visibility = View.VISIBLE

        // Устанавливаем текст ошибки
        val errorTextView = binding.errorState.findViewById<TextView>(R.id.errorMessageTextView)
        errorTextView?.text = error
    }

    private fun navigateToDetails(employee: Employee) {
        val action = CreateTaskSelectionEmployeeFragmentDirections
            .actionCreateTaskSelectionEmployeeFragmentToCreateTaskAddDetailsFragment(employee)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}