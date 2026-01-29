package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentSelectionTrailerBinding
import com.petproject.workflow.presentation.viewmodels.SelectionTrailerViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.TrailerAdapter
import javax.inject.Inject

class SelectionTrailerFragment : Fragment() {
    private var _binding: FragmentSelectionTrailerBinding? = null
    private val binding: FragmentSelectionTrailerBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[SelectionTrailerViewModel::class.java]
    }

    private val args by navArgs<SelectionTrailerFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private val trailerAdapter by lazy {
        TrailerAdapter { trailer ->
            args.selectionArg.onTrailerSelected(trailer)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentSelectionTrailerBinding.inflate(inflater, container, false)
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
    }

    private fun setupViews() {
        setupRecyclerView()
        setupSwipeRefresh()
//        setupSearch()
        setupRetryButton()
    }

    private fun setupRecyclerView() {
        with(binding.trailerListRecyclerView) {
            adapter = trailerAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.blue)
        )
    }

    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun setupObservers() {
        viewModel.trailerList.observe(viewLifecycleOwner) { trailer ->
            trailerAdapter.submitList(trailer)
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
        binding.trailerListRecyclerView.visibility = View.GONE
        binding.loadingState.visibility = View.VISIBLE
        binding.emptyState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
    }

    private fun showContentState() {
        val isEmpty = trailerAdapter.itemCount == 0
        binding.trailerListRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.loadingState.visibility = View.GONE
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.errorState.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
    }

    private fun showErrorState(error: String) {
        binding.trailerListRecyclerView.visibility = View.GONE
        binding.loadingState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.errorState.visibility = View.VISIBLE
        binding.retryButton.visibility = View.VISIBLE

        // Устанавливаем текст ошибки
        val errorTextView = binding.errorState.findViewById<TextView>(R.id.errorMessageTextView)
        errorTextView?.text = error
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}