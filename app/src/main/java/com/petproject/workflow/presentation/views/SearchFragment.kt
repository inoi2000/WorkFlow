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
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentSearchBinding
import com.petproject.workflow.presentation.viewmodels.SearchViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeAdapter
import javax.inject.Inject

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args by navArgs<SearchFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[SearchViewModel::class.java]
    }

    private val adapter by lazy {
        EmployeeAdapter(viewModel.requestManager) { employee ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToEmployeeInfoFragment(employee.id)
            findNavController().navigate(action)
        }
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication)
            .component
            .activityComponentFactory()
            .create(args.query)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupUI()
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.etSearch.setText(viewModel.queryText.value ?: "")
    }

    private fun setupUI() {
        setupRecyclerView()
        setupEditQuery()
        setupSwipeRefresh()
        setupErrorHandling()
    }

    private fun setupRecyclerView() {
        binding.employeeListRecyclerView.adapter = adapter
        binding.employeeListRecyclerView.itemAnimator = null
    }

    private fun setupEditQuery() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = requireContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                val searchQuery = binding.etSearch.text.toString().trim()
                if(searchQuery.isNotBlank()){
                    viewModel.updateQuery(searchQuery)
                    viewModel.refreshData()
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        binding.swipeRefreshLayout.setColorSchemeColors(
            requireContext().getColor(com.petproject.workflow.R.color.main_blue)
        )
    }

    private fun setupErrorHandling() {
        binding.retryButton.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun observeViewModel() {
        viewModel.employeeList.observe(viewLifecycleOwner) { employees ->
            binding.swipeRefreshLayout.isRefreshing = false

            if (employees.isNullOrEmpty()) {
                showEmptyState()
            } else {
                showContent()
                adapter.submitList(employees)
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading && (viewModel.employeeList.value.isNullOrEmpty())) {
                showLoading()
            }
        }

        viewModel.errorState.observe(viewLifecycleOwner) { error ->
            binding.swipeRefreshLayout.isRefreshing = false
            error?.let {
                showError(it)
            }
        }
    }

    private fun showLoading() {
        binding.loadingState.visibility = View.VISIBLE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.employeeListRecyclerView.visibility = View.GONE
    }

    private fun showContent() {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.employeeListRecyclerView.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.VISIBLE
        binding.emptyState.visibility = View.GONE
        binding.employeeListRecyclerView.visibility = View.GONE
        binding.errorText.text = errorMessage
    }

    private fun showEmptyState() {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
        binding.employeeListRecyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}