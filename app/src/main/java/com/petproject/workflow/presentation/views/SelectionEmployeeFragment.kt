package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentSelectionEmployeeBinding
import com.petproject.workflow.presentation.viewmodels.SelectionEmployeeViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeAdapter
import javax.inject.Inject

class SelectionEmployeeFragment : Fragment() {
    private var _binding: FragmentSelectionEmployeeBinding? = null
    private val binding: FragmentSelectionEmployeeBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[SelectionEmployeeViewModel::class.java]
    }

    private val args by navArgs<SelectionEmployeeFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private val employeeAdapter by lazy {
        EmployeeAdapter(viewModel.requestManager) { employee ->
            args.selectionArg.onEmployeeSelected(employee)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentSelectionEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
        setupMenuProvider()
        setupObservers()
    }

    private fun setupViewModel() {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loadData { args.selectionArg.getEmployees() }
    }

    private fun setupViews() {
        setupRecyclerView()
        setupSwipeRefresh()
        setupSearch()
        setupRetryButton()
    }

    private fun setupMenuProvider() {
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.selection_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_filter_off -> {
                        viewModel.clearFilter()
                        true
                    }
                    else -> false
                }
            }
        }
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
            viewModel.loadData(false) { args.selectionArg.getEmployees() }
        }
        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.blue)
        )
    }

    private fun setupSearch() {
        binding.searchEditText.doAfterTextChanged { text ->
            viewModel.filterEmployees(text.toString())
        }
    }

    private fun setupRetryButton() {
        binding.updateErrorStateButton.setOnClickListener {
            viewModel.loadData { args.selectionArg.getEmployees() }
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
    }

    private fun showContentState() {
        val isEmpty = employeeAdapter.itemCount == 0
        binding.employeeListRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.loadingState.visibility = View.GONE
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.errorState.visibility = View.GONE
    }

    private fun showErrorState(error: String) {
        binding.employeeListRecyclerView.visibility = View.GONE
        binding.loadingState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.errorState.visibility = View.VISIBLE

        // Устанавливаем текст ошибки
        binding.errorText.text = error
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}