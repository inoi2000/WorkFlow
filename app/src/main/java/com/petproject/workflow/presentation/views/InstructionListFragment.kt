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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentInstructionListBinding
import com.petproject.workflow.domain.entities.Instruction
import com.petproject.workflow.presentation.viewmodels.InstructionListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.InstructionAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructionListFragment : Fragment() {
    private var _binding: FragmentInstructionListBinding? = null
    private val binding: FragmentInstructionListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[InstructionListViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private lateinit var adapter: InstructionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentInstructionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        setupRecyclerView()
        setupSwipeRefresh()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        adapter = InstructionAdapter().apply {
            onItemClickListener = { instruction ->
                // Обработка клика по инструктажу
//                navigateToInstructionDetails(instruction.id)
            }
        }
        binding.instructionListRecyclerView.apply {
            adapter = this@InstructionListFragment.adapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
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

    private fun setupSearchView() {
        binding.searchEditText.apply {
            // Слушатель изменения текста с задержкой для избежания частых обновлений
            doAfterTextChanged { editable ->
                val query = editable?.toString() ?: ""
                // Дебаунс для избежания частых обновлений
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(300) // Задержка 300мс
                    viewModel.updateSearchQuery(query)
                }
            }

            // Обработка нажатия Enter/Search
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
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
                    viewModel.searchQuery.collect { query ->
                        filterInstructions(query)
                    }
                }
            }
        }
    }

    private fun handleUiState(uiState: InstructionListViewModel.InstructionListUiState) {
        binding.swipeRefreshLayout.isRefreshing = false

        when (uiState) {
            is InstructionListViewModel.InstructionListUiState.Loading -> {
                showLoading()
            }

            is InstructionListViewModel.InstructionListUiState.Success -> {
                showInstructions(uiState.instructions)
            }

            is InstructionListViewModel.InstructionListUiState.Error -> {
                showError(uiState.message)
            }
        }
    }

    private fun showLoading() {
        binding.instructionListRecyclerView.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showInstructions(instructions: List<Instruction>) {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE

        if (instructions.isEmpty()) {
            binding.instructionListRecyclerView.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
            binding.emptyStateText.text = getString(R.string.no_instructions_available)
        } else {
            binding.instructionListRecyclerView.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
            adapter.submitList(instructions)
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.instructionListRecyclerView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE

        binding.errorText.text = message
        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }
    }

    private fun filterInstructions(query: String) {
        val currentList = (viewModel.uiState.value as?
                InstructionListViewModel.InstructionListUiState.Success)?.instructions
        currentList?.let { instructions ->
            val filtered = if (query.isBlank()) {
                instructions
            } else {
                instructions.filter { instruction ->
                    instruction.data.content.contains(query, true) ||
                            instruction.status.name.contains(query, true)
                }
            }
            adapter.submitList(filtered)

            // Показать/скрыть empty state для поиска
            if (filtered.isEmpty() && query.isNotBlank()) {
                binding.instructionListRecyclerView.visibility = View.GONE
                binding.emptyStateLayout.visibility = View.VISIBLE
                binding.emptyStateText.text = getString(R.string.no_results_found, query)
            } else if (filtered.isEmpty()) {
                binding.instructionListRecyclerView.visibility = View.GONE
                binding.emptyStateLayout.visibility = View.VISIBLE
                binding.emptyStateText.text = getString(R.string.no_instructions_available)
            } else {
                binding.instructionListRecyclerView.visibility = View.VISIBLE
                binding.emptyStateLayout.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}