package com.petproject.workflow.presentation.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentStorageListBinding
import com.petproject.workflow.domain.entities.Storage
import com.petproject.workflow.presentation.viewmodels.StorageListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.CarAdapter
import com.petproject.workflow.presentation.views.adapters.StorageAdapter
import com.petproject.workflow.presentation.views.adapters.TaskAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class StorageListFragment : Fragment() {
    private var _binding: FragmentStorageListBinding? = null
    private val binding: FragmentStorageListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[StorageListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private val storageAdapter by lazy {
        StorageAdapter { storage ->
//            val action = InspectorTaskListFragmentDirections
//                .actionInspectorTaskListFragmentToInspectorTaskInfoFragment(taskId)
//            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentStorageListBinding.inflate(inflater, container, false)
        setRecyclerView()
        setupSearch()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupSwipeRefresh()
        observeViewModel()
    }

    private fun setupClickListeners() {
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
                    viewModel.searchStorage(query)
                }
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // Search as you type with debounce
        binding.etSearch.doAfterTextChanged { text ->
            viewModel.searchStorage(text?.toString()?.trim() ?: "")
        }
    }

    private fun setRecyclerView() {
        binding.storageListRecyclerView.itemAnimator = null
        binding.storageListRecyclerView.adapter = storageAdapter
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
                    viewModel.filteredStorageList.observe(viewLifecycleOwner) { storage ->
                        storageAdapter.submitList(storage)
                    }
                }
            }
        }
    }

    private fun handleUiState(uiState: StorageListViewModel.StorageListUiState) {
        binding.swipeRefreshLayout.isRefreshing = false

        when (uiState) {
            is StorageListViewModel.StorageListUiState.Loading -> {
                showLoading()
            }
            is StorageListViewModel.StorageListUiState.Success -> {
                showStorages(uiState.storages)
            }
            is StorageListViewModel.StorageListUiState.Error -> {
                showError(uiState.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.storageListRecyclerView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showStorages(tasks: List<Storage>) {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE

        val filteredTasks = viewModel.filteredStorageList.value ?: tasks

        if (filteredTasks.isEmpty()) {
            binding.storageListRecyclerView.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        } else {
            binding.storageListRecyclerView.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.storageListRecyclerView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorText.text = message
    }

    private fun clearAllFilters() {
        // Clear search
        binding.etSearch.text?.clear()

        // Reset filters in ViewModel
        viewModel.clearAllFilters()

        // Hide keyboard
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}