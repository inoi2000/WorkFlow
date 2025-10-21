package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentAbsenceListBinding
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.presentation.viewmodels.AbsenceViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.AbsenceAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AbsenceListFragment : Fragment() {
    private var _binding: FragmentAbsenceListBinding? = null
    private val binding: FragmentAbsenceListBinding get() = _binding!!

    private val args by navArgs<AbsenceListFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AbsenceViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private lateinit var adapter: AbsenceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentAbsenceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        setupSearch()
        setupSwipeRefresh()
        setInitialMode()
    }

    private fun setupRecyclerView() {
        adapter = AbsenceAdapter(AbsenceAdapter.EMPLOYEE_MODE) { absenceId ->
            //TODO
        }
        binding.absenceListRecyclerView.apply {
            adapter = this@AbsenceListFragment.adapter
            itemAnimator = null
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filteredAbsenceList.collectLatest { absences ->
                adapter.submitList(absences)
                updateEmptyState(absences.isEmpty())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                when (uiState) {
                    is AbsenceViewModel.AbsenceListUiState.Loading -> {
                        showLoading(true)
                        showError(false)
                        showEmptyState(false)
                    }
                    is AbsenceViewModel.AbsenceListUiState.Success -> {
                        showLoading(false)
                        showError(false)
                        updateEmptyState(uiState.absences.isEmpty())
                    }
                    is AbsenceViewModel.AbsenceListUiState.Error -> {
                        showLoading(false)
                        showError(true, uiState.message)
                        showEmptyState(false)
                    }
                }
            }
        }

        // Наблюдаем за счетчиками
        viewModel.vacationCount.observe(viewLifecycleOwner) { count ->
            binding.vacationsCounterTextView.text = count.toString()
        }
        viewModel.businessTripCount.observe(viewLifecycleOwner) { count ->
            binding.businessTripsCounterTextView.text = count.toString()
        }
        viewModel.sickLeaveCount.observe(viewLifecycleOwner) { count ->
            binding.sickLeavesCounterTextView.text = count.toString()
        }
        viewModel.dayOffCount.observe(viewLifecycleOwner) { count ->
            binding.daysOffCounterTextView.text = count.toString()
        }
    }

    private fun setupClickListeners() {
        binding.vacationsCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.VACATION)
            updateTitle(getString(R.string.vacations))
            selectCardView(binding.vacationsCardView)
        }

        binding.businessTripsCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.BUSINESS_TRIP)
            updateTitle(getString(R.string.business_trips))
            selectCardView(binding.businessTripsCardView)
        }

        binding.sickLeavesCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.SICK_LEAVE)
            updateTitle(getString(R.string.sick_leaves))
            selectCardView(binding.sickLeavesCardView)
        }

        binding.daysOffCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.DAY_OFF)
            updateTitle(getString(R.string.days_off))
            selectCardView(binding.daysOffCardView)
        }

        binding.filterOffImageView.setOnClickListener {
            viewModel.clearAllFilters()
            updateTitle("Все отсутствия")
            clearCardViewSelection()
        }

        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }
    }

    private fun setupSearch() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                val query = binding.etSearch.text?.toString() ?: ""
                viewModel.searchAbsences(query)
                true
            } else {
                false
            }
        }

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val query = binding.etSearch.text?.toString() ?: ""
                viewModel.searchAbsences(query)
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setInitialMode() {
        when (args.absenceType) {
            AbsenceType.VACATION -> binding.vacationsCardView.performClick()
            AbsenceType.BUSINESS_TRIP -> binding.businessTripsCardView.performClick()
            AbsenceType.SICK_LEAVE -> binding.sickLeavesCardView.performClick()
            AbsenceType.DAY_OFF -> binding.daysOffCardView.performClick()
            else -> clearCardViewSelection()
        }
    }

    private fun selectCardView(selectedCardView: CardView) {
        // Скрываем все селекторы
        binding.vacationsSelector.visibility = View.GONE
        binding.businessTripsSelector.visibility = View.GONE
        binding.sickLeavesSelector.visibility = View.GONE
        binding.daysOffSelector.visibility = View.GONE

        // Показываем селектор выбранной карточки
        when (selectedCardView) {
            binding.vacationsCardView -> binding.vacationsSelector.visibility = View.VISIBLE
            binding.businessTripsCardView -> binding.businessTripsSelector.visibility = View.VISIBLE
            binding.sickLeavesCardView -> binding.sickLeavesSelector.visibility = View.VISIBLE
            binding.daysOffCardView -> binding.daysOffSelector.visibility = View.VISIBLE
        }
    }

    private fun clearCardViewSelection() {
        binding.vacationsSelector.visibility = View.GONE
        binding.businessTripsSelector.visibility = View.GONE
        binding.sickLeavesSelector.visibility = View.GONE
        binding.daysOffSelector.visibility = View.GONE
    }

    private fun updateTitle(title: String) {
        binding.titleTextView.text = title
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.absenceListRecyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun showError(show: Boolean, message: String? = null) {
        binding.errorLayout.visibility = if (show) View.VISIBLE else View.GONE
        binding.absenceListRecyclerView.visibility = if (show) View.GONE else View.VISIBLE

        message?.let {
            binding.errorText.text = it
        }
    }

    private fun showEmptyState(show: Boolean) {
        binding.emptyStateLayout.visibility = if (show) View.VISIBLE else View.GONE
        binding.absenceListRecyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty && viewModel.uiState.value is AbsenceViewModel.AbsenceListUiState.Success) {
            showEmptyState(true)
        } else {
            showEmptyState(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}