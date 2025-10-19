package com.petproject.workflow.presentation.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentInspectorTaskListBinding
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.InspectorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import com.petproject.workflow.presentation.views.adapters.TaskInfoViewHolder
import kotlinx.coroutines.launch
import javax.inject.Inject

class InspectorTaskListFragment : Fragment() {
    private var _binding: FragmentInspectorTaskListBinding? = null
    private val binding: FragmentInspectorTaskListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[InspectorTaskListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentInspectorTaskListBinding.inflate(inflater, container, false)
        setRecyclerView()
        setupSearch()
        setupSpinners()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupSwipeRefresh()
        observeViewModel()

        // Auto-select "On Approval" tab on start
        binding.onApprovalTasksCardView.callOnClick()
    }

    private fun setupClickListeners() {
        binding.onApprovalTasksCardView.setOnClickListener {
            // При клике на карточку - устанавливаем фильтр по статусу и сбрасываем спиннеры
            resetSpinners()
            viewModel.filteredTaskListByStatus(TaskStatus.ON_APPROVAL)
            updateCardSelection()
        }
        binding.allTasksCardView.setOnClickListener {
            // При клике на карточку - сбрасываем спиннеры
            resetSpinners()
            viewModel.filteredTaskListByDefault()
            updateCardSelection()
        }

        // Clear filters when filter icon is clicked
        binding.filterOffImageView.setOnClickListener {
            clearAllFilters()
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

    private fun setupSearch() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val query = binding.etSearch.text?.toString()?.trim()
                if (!query.isNullOrEmpty()) {
                    viewModel.searchTasks(query)
                }
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // Search as you type with debounce
        binding.etSearch.doAfterTextChanged { text ->
            viewModel.searchTasks(text?.toString()?.trim() ?: "")
        }
    }

    private fun setupSpinners() {
        // Status spinner
        binding.taskStatusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position > 0) { // Skip "All" option
                    val status = when (position) {
                        1 -> TaskStatus.NEW
                        2 -> TaskStatus.IN_PROGRESS
                        3 -> TaskStatus.ON_APPROVAL
                        4 -> TaskStatus.COMPLETED
                        5 -> TaskStatus.FAILED
                        6 -> TaskStatus.NOT_APPROVAL
                        7 -> TaskStatus.CANCELED
                        else -> null
                    }
                    status?.let {
                        // При выборе в спиннере - сбрасываем выделение карточек
                        clearCardSelection()
                        viewModel.filteredTaskListByStatus(it)
                    }
                } else {
                    // "All" selected - сбрасываем фильтры
                    clearCardSelection()
                    viewModel.filteredTaskListByDefault()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Priority spinner
        binding.taskPrioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position > 0) { // Skip "All" option
                    val priority = when (position) {
                        1 -> TaskPriority.COMMON
                        2 -> TaskPriority.URGENT
                        else -> null
                    }
                    priority?.let {
                        // При выборе в спиннере - сбрасываем выделение карточек
                        clearCardSelection()
                        viewModel.filteredTaskListByPriority(it)
                    }
                } else {
                    // "All" selected - сбрасываем фильтры
                    clearCardSelection()
                    viewModel.filteredTaskListByDefault()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setRecyclerView() {
        val adapter = TaskAdapter(
            TaskInfoViewHolder.INSPECTOR_MODE,
            { taskId ->
                val action = InspectorTaskListFragmentDirections
                    .actionInspectorTaskListFragmentToInspectorTaskInfoFragment(taskId)
                findNavController().navigate(action)
            },
            { taskId ->
                // Navigate to comments if needed
                // val action = InspectorTaskListFragmentDirections
                //     .actionInspectorTaskListFragmentToTaskCommentListFragment(taskId)
                // findNavController().navigate(action)
            }
        )
        binding.tasksListRecyclerView.itemAnimator = null
        binding.tasksListRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                        handleUiState(uiState)
                    }
                }
                launch {
                    viewModel.filteredTaskList.observe(viewLifecycleOwner) { tasks ->
                        (binding.tasksListRecyclerView.adapter as? TaskAdapter)?.submitList(tasks)
                        // Обновляем выделение карточек при изменении фильтрованных данных
                        updateCardSelection()
                    }
                }
            }
        }
    }

    private fun handleUiState(uiState: InspectorTaskListViewModel.TaskListUiState) {
        binding.swipeRefreshLayout.isRefreshing = false

        when (uiState) {
            is InspectorTaskListViewModel.TaskListUiState.Loading -> {
                showLoading()
            }
            is InspectorTaskListViewModel.TaskListUiState.Success -> {
                showTasks(uiState.tasks)
            }
            is InspectorTaskListViewModel.TaskListUiState.Error -> {
                showError(uiState.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tasksListRecyclerView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showTasks(tasks: List<com.petproject.workflow.domain.entities.Task>) {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE

        val filteredTasks = viewModel.filteredTaskList.value ?: tasks

        if (filteredTasks.isEmpty()) {
            binding.tasksListRecyclerView.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        } else {
            binding.tasksListRecyclerView.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.tasksListRecyclerView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorText.text = message
    }

    private fun clearAllFilters() {
        // Clear search
        binding.etSearch.text?.clear()

        // Reset spinners to "All" position
        resetSpinners()

        // Clear card selection
        clearCardSelection()

        // Reset filters in ViewModel
        viewModel.clearAllFilters()

        // Hide keyboard
        hideKeyboard()
    }

    private fun clearCardSelection() {
        binding.onApprovalTasksSelector.visibility = View.GONE
        binding.allTasksSelector.visibility = View.GONE
    }

    private fun updateCardSelection() {
        clearCardSelection()

        // Определяем какая карточка должна быть выделена на основе активного фильтра
        when {
            viewModel.getActiveStatusFilter() == TaskStatus.ON_APPROVAL -> {
                binding.onApprovalTasksSelector.visibility = View.VISIBLE
            }
            viewModel.isAllTasksFilterActive() -> {
                binding.allTasksSelector.visibility = View.VISIBLE
            }
        }
    }

    private fun resetSpinners() {
        binding.taskStatusSpinner.setSelection(0)
        binding.taskPrioritySpinner.setSelection(0)
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}