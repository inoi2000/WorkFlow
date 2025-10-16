package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentJourneyListBinding
import com.petproject.workflow.presentation.viewmodels.JourneyListViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.JourneyAdapter
import javax.inject.Inject

class JourneyListFragment : Fragment() {
    private var _binding: FragmentJourneyListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[JourneyListViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private lateinit var adapter: JourneyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentJourneyListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = JourneyAdapter() { journeyId ->
            navigateToJourneyDetails(journeyId)
        }

        binding.journeyListRecyclerView.adapter = adapter
        binding.journeyListRecyclerView.itemAnimator = null
        with(binding.journeyListRecyclerView) {
            // Добавляем разделители между элементами
            if (itemDecorationCount == 0) {
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            }
        }
    }

    private fun setupObservers() {
        viewModel.journeyList.observe(viewLifecycleOwner) { journeys ->
            adapter.submitList(journeys)

            // Показываем empty state если список пустой
            if (journeys.isNullOrEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showError(it)
                viewModel.errorMessageShown()
            }
        }
    }

    private fun navigateToJourneyDetails(journeyId: String) {
        // Навигация к деталям выезда
//        findNavController().navigate(
//            JourneyListFragmentDirections.actionJourneyListFragmentToJourneyDetailFragment(journeyId)
//        )
    }

    private fun showEmptyState() {
        // Показываем состояние пустого списка
        binding.emptyStateView.visibility = View.VISIBLE
        binding.journeyListRecyclerView.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.emptyStateView.visibility = View.GONE
        binding.journeyListRecyclerView.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}