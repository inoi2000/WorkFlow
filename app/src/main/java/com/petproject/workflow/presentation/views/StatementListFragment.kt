package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentStatementListBinding
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.presentation.viewmodels.StatementListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.StatementAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class StatementListFragment : Fragment() {
    private var _binding: FragmentStatementListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[StatementListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private lateinit var adapter: StatementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentStatementListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем Toolbar как ActionBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        // Добавляем MenuProvider
        setupMenuProvider()

        setupRecyclerView()
        setupUI()
        setupObservers()
        setupCalendar()
    }

    private fun setupMenuProvider() {
        // Создаем MenuProvider
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                println("DEBUG: onCreateMenu called")
                menuInflater.inflate(R.menu.filter_list_menu, menu)

                // Проверка что меню создано
                val calendarItem = menu.findItem(R.id.action_calendar)
                val filterItem = menu.findItem(R.id.action_filter_off)
                println("DEBUG: Calendar item found: ${calendarItem != null}")
                println("DEBUG: Filter item found: ${filterItem != null}")
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                println("DEBUG: onMenuItemSelected: ${menuItem.title} (ID: ${menuItem.itemId})")

                return when (menuItem.itemId) {
                    R.id.action_calendar -> {
                        println("DEBUG: Calendar button clicked!")
                        viewModel.toggleCalendar()
                        true
                    }

                    R.id.action_filter_off -> {
                        println("DEBUG: Clear filter button clicked!")
                        viewModel.clearFilter()
                        true
                    }

                    else -> false
                }
            }
        }

        // Добавляем MenuProvider к Activity
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupCalendar() {
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            println("DEBUG: Date selected: $dayOfMonth.${month + 1}.$year")
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            val dateMillis =
                selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            viewModel.onDateSelected(dateMillis)
        }
    }

    private fun setupUI() {
        setupSwipeRefresh()
    }

    private fun setupRecyclerView() {
        adapter = StatementAdapter { statementId ->
            navigateToStatementDetails(statementId)
        }

        with(binding.statementListRecyclerView) {
            adapter = this@StatementListFragment.adapter
            itemAnimator = null
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadData()
        }

        binding.swipeRefreshLayout.setColorSchemeResources(
            R.color.main_blue,
            R.color.green,
            R.color.orange
        )
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        handleUiState(uiState)
                    }
                }
                launch {
                    viewModel.showCalendar.collect { show ->
                        println("DEBUG: showCalendar changed to: $show")
                        binding.calendarView.visibility = if (show) View.VISIBLE else View.GONE

                        if (show) {
                            binding.statementListRecyclerView.visibility = View.VISIBLE
                            binding.emptyStateLayout.visibility = View.GONE
                            binding.errorLayout.visibility = View.GONE
                        } else {
                            handleCurrentUiState()
                        }
                    }
                }
            }
        }
    }

    private fun handleCurrentUiState() {
        when (val currentState = viewModel.uiState.value) {
            is StatementListViewModel.StatementListUiState.Loading -> showLoading()
            is StatementListViewModel.StatementListUiState.Success -> showStatements(currentState.statements)
            is StatementListViewModel.StatementListUiState.Error -> showError(currentState.message)
        }
    }

    private fun handleUiState(uiState: StatementListViewModel.StatementListUiState) {
        binding.swipeRefreshLayout.isRefreshing = false

        when (uiState) {
            is StatementListViewModel.StatementListUiState.Loading -> {
                showLoading()
            }

            is StatementListViewModel.StatementListUiState.Success -> {
                showStatements(uiState.statements)
            }

            is StatementListViewModel.StatementListUiState.Error -> {
                showError(uiState.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.statementListRecyclerView.visibility = View.GONE
        binding.calendarView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showStatements(statements: List<Statement>) {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE

        adapter.submitList(statements)

        if (statements.isEmpty()) {
            binding.statementListRecyclerView.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        } else {
            binding.statementListRecyclerView.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.statementListRecyclerView.visibility = View.GONE
        binding.calendarView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorText.text = message
    }

    private fun navigateToStatementDetails(statementId: String) {
        // TODO: Навигация к деталям выезда
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}