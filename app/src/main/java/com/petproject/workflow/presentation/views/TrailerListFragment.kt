package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentTrailerListBinding
import com.petproject.workflow.presentation.viewmodels.TrailerListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TrailerAdapter
import javax.inject.Inject

class TrailerListFragment : Fragment() {
    private var _binding: FragmentTrailerListBinding? = null
    private val binding: FragmentTrailerListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[TrailerListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentTrailerListBinding.inflate(inflater, container, false)
        setupUI()
        observeViewModel()
        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        setupSwipeRefresh()
        setupErrorHandling()
    }

    private fun setupRecyclerView() {
        val adapter = TrailerAdapter()
        binding.trailerListRecyclerView.adapter = adapter
        binding.trailerListRecyclerView.itemAnimator = null
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        binding.swipeRefreshLayout.setColorSchemeColors(
            requireContext().getColor(R.color.main_blue)
        )
    }

    private fun setupErrorHandling() {
        binding.retryButton.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun observeViewModel() {
        viewModel.trailerList.observe(viewLifecycleOwner) { trailer ->
            binding.swipeRefreshLayout.isRefreshing = false

            if (trailer.isNullOrEmpty()) {
                showEmptyState()
            } else {
                showContent()
                (binding.trailerListRecyclerView.adapter as TrailerAdapter).submitList(trailer)
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading && (viewModel.trailerList.value.isNullOrEmpty())) {
                showLoading()
            }
        }

        viewModel.errorState.observe(viewLifecycleOwner) { error ->
            binding.swipeRefreshLayout.isRefreshing = false
            error?.let {
                showError(it)
            }
        }
    }

    private fun showLoading() {
        binding.loadingState.visibility = View.VISIBLE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.trailerListRecyclerView.visibility = View.GONE
    }

    private fun showContent() {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.trailerListRecyclerView.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.VISIBLE
        binding.emptyState.visibility = View.GONE
        binding.trailerListRecyclerView.visibility = View.GONE
        binding.errorText.text = errorMessage
    }

    private fun showEmptyState() {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
        binding.trailerListRecyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}