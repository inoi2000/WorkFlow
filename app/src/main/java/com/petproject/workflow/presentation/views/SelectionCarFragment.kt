package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentSelectionCarBinding
import com.petproject.workflow.presentation.viewmodels.SelectionCarViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.CarAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectionCarFragment : Fragment() {
    private var _binding: FragmentSelectionCarBinding? = null
    private val binding: FragmentSelectionCarBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[SelectionCarViewModel::class.java]
    }

    private val args by navArgs<SelectionCarFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private val carAdapter by lazy {
        CarAdapter { car ->
            args.selectionArg.onCarSelected(car)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentSelectionCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
        setupObservers()
    }

    private fun setupViewModel() {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loadData { args.selectionArg.getCars() }
    }

    private fun setupViews() {
        setupToolbar()
        setupRecyclerView()
        setupSwipeRefresh()
        setupSearch()
        setupRetryButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_filter_off -> {
                    viewModel.clearFilter()
                    binding.searchEditText.text?.clear()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.carListRecyclerView) {
            adapter = carAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadData(false) { args.selectionArg.getCars() }
        }
        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.blue)
        )
    }

    private var searchJob: Job? = null

    private fun setupSearch() {
        binding.searchEditText.doAfterTextChanged { text ->
            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(300)
                viewModel.filterCars(text.toString())
            }
        }
    }

    private fun setupRetryButton() {
        binding.updateErrorStateButton.setOnClickListener {
            viewModel.loadData { args.selectionArg.getCars() }
        }
    }

    private fun setupObservers() {
        viewModel.carList.observe(viewLifecycleOwner) { car ->
            carAdapter.submitList(car)
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
        binding.carListRecyclerView.visibility = View.GONE
        binding.loadingState.visibility = View.VISIBLE
        binding.emptyState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
    }

    private fun showContentState() {
        val isEmpty = carAdapter.itemCount == 0
        binding.carListRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.loadingState.visibility = View.GONE
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.errorState.visibility = View.GONE
    }

    private fun showErrorState(error: String) {
        binding.carListRecyclerView.visibility = View.GONE
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