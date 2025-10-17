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
import com.petproject.workflow.databinding.FragmentJourneyListBinding
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.presentation.viewmodels.JourneyListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.JourneyAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class JourneyListFragment : Fragment() {
    private var _binding: FragmentJourneyListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[JourneyListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private lateinit var adapter: JourneyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentJourneyListBinding.inflate(inflater, container, false)
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
            val dateMillis = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            viewModel.onDateSelected(dateMillis)
        }
    }

    private fun setupUI() {
        setupSwipeRefresh()
    }

    private fun setupRecyclerView() {
        adapter = JourneyAdapter { journeyId ->
            navigateToJourneyDetails(journeyId)
        }

        with(binding.journeyListRecyclerView) {
            adapter = this@JourneyListFragment.adapter
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
                            binding.journeyListRecyclerView.visibility = View.VISIBLE
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
            is JourneyListViewModel.JourneyListUiState.Loading -> showLoading()
            is JourneyListViewModel.JourneyListUiState.Success -> showJourneys(currentState.journeys)
            is JourneyListViewModel.JourneyListUiState.Error -> showError(currentState.message)
        }
    }

    private fun handleUiState(uiState: JourneyListViewModel.JourneyListUiState) {
        binding.swipeRefreshLayout.isRefreshing = false

        when (uiState) {
            is JourneyListViewModel.JourneyListUiState.Loading -> {
                showLoading()
            }
            is JourneyListViewModel.JourneyListUiState.Success -> {
                showJourneys(uiState.journeys)
            }
            is JourneyListViewModel.JourneyListUiState.Error -> {
                showError(uiState.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.journeyListRecyclerView.visibility = View.GONE
        binding.calendarView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showJourneys(journeys: List<Journey>) {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE

        adapter.submitList(journeys)

        if (journeys.isEmpty()) {
            binding.journeyListRecyclerView.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        } else {
            binding.journeyListRecyclerView.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.journeyListRecyclerView.visibility = View.GONE
        binding.calendarView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorText.text = message
    }

    private fun navigateToJourneyDetails(journeyId: String) {
        // TODO: Навигация к деталям выезда
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}