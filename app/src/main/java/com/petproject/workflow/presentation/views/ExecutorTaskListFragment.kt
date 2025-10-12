package com.petproject.workflow.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentExecutorTaskListBinding
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import com.petproject.workflow.presentation.views.adapters.TaskInfoViewHolder
import javax.inject.Inject

class ExecutorTaskListFragment : Fragment() {
    private var _binding: FragmentExecutorTaskListBinding? = null
    private val binding: FragmentExecutorTaskListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[ExecutorTaskListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentExecutorTaskListBinding.inflate(inflater, container, false)
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
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.overdueTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.FAILED)
            choseCardView(binding.overdueTasksCardView)
        }
        binding.urgentTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByPriority(TaskPriority.URGENT)
            choseCardView(binding.urgentTasksCardView)
        }
        binding.onApprovalTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.ON_APPROVAL)
            choseCardView(binding.onApprovalTasksCardView)
        }
        binding.finishedTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.COMPLETED)
            choseCardView(binding.finishedTasksCardView)
        }
    }

    private fun setupSearch() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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
                        viewModel.filteredTaskListByStatus(it)
                        clearCardSelection()
                    }
                } else {
                    viewModel.filteredTaskListByDefault()
                    clearCardSelection()
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
                        viewModel.filteredTaskListByPriority(it)
                        clearCardSelection()
                    }
                } else {
                    viewModel.filteredTaskListByDefault()
                    clearCardSelection()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setRecyclerView() {
        viewModel.loadData()
        val adapter = TaskAdapter(
            TaskInfoViewHolder.EXECUTOR_MODE,
            { taskId ->
                navigateToTaskDetails(taskId)
            },
            { taskId ->
                navigateToComments(taskId)
            }
        )
        binding.tasksListRecyclerView.itemAnimator = null
        binding.tasksListRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.filteredTaskList.observe(viewLifecycleOwner) { tasks ->
            (binding.tasksListRecyclerView.adapter as? TaskAdapter)?.submitList(tasks)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // You can show/hide progress bar here if needed
        }
    }

    private fun choseCardView(cardView: CardView) {
        clearCardSelection()

        when (cardView) {
            binding.overdueTasksCardView -> {
                binding.overdueTasksSelector.visibility = View.VISIBLE
            }
            binding.urgentTasksCardView -> {
                binding.urgentTasksSelector.visibility = View.VISIBLE
            }
            binding.onApprovalTasksCardView -> {
                binding.onApprovalTasksSelector.visibility = View.VISIBLE
            }
            binding.finishedTasksCardView -> {
                binding.finishedTasksSelector.visibility = View.VISIBLE
            }
        }
    }

    private fun clearCardSelection() {
        binding.overdueTasksSelector.visibility = View.GONE
        binding.urgentTasksSelector.visibility = View.GONE
        binding.onApprovalTasksSelector.visibility = View.GONE
        binding.finishedTasksSelector.visibility = View.GONE
    }

    private fun navigateToTaskDetails(taskId: String) {
        val action = ExecutorTaskListFragmentDirections
            .actionExecutingTaskListFragmentToExecutingTaskInfoFragment(taskId)
        findNavController().navigate(action)
    }

    private fun navigateToComments(taskId: String) {
        val action = ExecutorTaskListFragmentDirections
            .actionExecutingTaskListFragmentToTaskCommentListFragment(
                taskId,
                TaskCommentListFragment.MODE_FROM_EXECUTOR
            )
        findNavController().navigate(action)
    }

    @SuppressLint("ServiceCast")
    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}