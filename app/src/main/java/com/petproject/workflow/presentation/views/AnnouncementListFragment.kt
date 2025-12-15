package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentAnnouncementListBinding
import com.petproject.workflow.presentation.viewmodels.AnnouncementListViewModel
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.AccessAdapter
import com.petproject.workflow.presentation.views.adapters.AnnouncementAdapter
import javax.inject.Inject

class AnnouncementListFragment : Fragment() {
    private var _binding: FragmentAnnouncementListBinding? = null
    private val binding: FragmentAnnouncementListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[AnnouncementListViewModel::class]
    }

    private val adapter by lazy {
        AnnouncementAdapter(viewModel.requestManager)
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentAnnouncementListBinding.inflate(inflater, container, false)
        setupUI()
        observeViewModel()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        setupSwipeRefresh()
        setupErrorHandling()
    }

    private fun setupRecyclerView() {
        binding.announcementListRecyclerView.adapter = adapter
        binding.announcementListRecyclerView.itemAnimator = null
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        binding.swipeRefreshLayout.setColorSchemeColors(
            requireContext().getColor(com.petproject.workflow.R.color.main_blue)
        )
    }

    private fun setupErrorHandling() {
        binding.retryButton.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun observeViewModel() {
        viewModel.announcementList.observe(viewLifecycleOwner) { announcements ->
            binding.swipeRefreshLayout.isRefreshing = false

            if (announcements.isNullOrEmpty()) {
                showEmptyState()
            } else {
                showContent()
                adapter.submitList(announcements)
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading && (viewModel.announcementList.value.isNullOrEmpty())) {
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
        binding.announcementListRecyclerView.visibility = View.GONE
    }

    private fun showContent() {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.announcementListRecyclerView.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.VISIBLE
        binding.emptyState.visibility = View.GONE
        binding.announcementListRecyclerView.visibility = View.GONE
        binding.errorText.text = errorMessage
    }

    private fun showEmptyState() {
        binding.loadingState.visibility = View.GONE
        binding.errorState.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
        binding.announcementListRecyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}