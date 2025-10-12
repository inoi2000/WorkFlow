package com.petproject.workflow.presentation.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentInspectorTaskListBinding
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.InspectorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import com.petproject.workflow.presentation.views.adapters.TaskInfoViewHolder
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
        observeViewModel()

        // Auto-select "On Approval" tab on start
        binding.onApprovalTasksCardView.callOnClick()
    }

    private fun setupClickListeners() {
        binding.onApprovalTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByStatus(TaskStatus.ON_APPROVAL)
            choseCardView(binding.onApprovalTasksCardView)
        }
        binding.allTasksCardView.setOnClickListener {
            viewModel.filteredTaskListByDefault()
            choseCardView(binding.allTasksCardView)
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
            binding.onApprovalTasksCardView -> {
                binding.onApprovalTasksSelector.visibility = View.VISIBLE
            }
            binding.allTasksCardView -> {
                binding.allTasksSelector.visibility = View.VISIBLE
            }
        }
    }

    private fun clearCardSelection() {
        binding.onApprovalTasksSelector.visibility = View.GONE
        binding.allTasksSelector.visibility = View.GONE
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