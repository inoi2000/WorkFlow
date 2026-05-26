package com.petproject.workflow.presentation.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentProductListBinding
import com.petproject.workflow.domain.entities.Product
import com.petproject.workflow.presentation.viewmodels.ProductListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.ProductAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding: FragmentProductListBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[ProductListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private val productAdapter by lazy {
        ProductAdapter { product ->


            // TODO
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
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
                    viewModel.searchProduct(query)
                }
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // Search as you type with debounce
        binding.etSearch.doAfterTextChanged { text ->
            viewModel.searchProduct(text?.toString()?.trim() ?: "")
        }
    }

    private fun setRecyclerView() {
        binding.productListRecyclerView.itemAnimator = null
        binding.productListRecyclerView.adapter = productAdapter
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
                    viewModel.filteredProductList.observe(viewLifecycleOwner) { product ->
                        productAdapter.submitList(product)
                    }
                }
            }
        }
    }

    private fun handleUiState(uiState: ProductListViewModel.ProductListUiState) {
        binding.swipeRefreshLayout.isRefreshing = false

        when (uiState) {
            is ProductListViewModel.ProductListUiState.Loading -> {
                showLoading()
            }
            is ProductListViewModel.ProductListUiState.Success -> {
                showProducts(uiState.products)
            }
            is ProductListViewModel.ProductListUiState.Error -> {
                showError(uiState.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.productListRecyclerView.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showProducts(tasks: List<Product>) {
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE

        val filteredTasks = viewModel.filteredProductList.value ?: tasks

        if (filteredTasks.isEmpty()) {
            binding.productListRecyclerView.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        } else {
            binding.productListRecyclerView.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.productListRecyclerView.visibility = View.GONE
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