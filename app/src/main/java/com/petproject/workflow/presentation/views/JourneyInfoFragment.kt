package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentJourneyInfoBinding
import com.petproject.workflow.domain.entities.JourneyStatus
import com.petproject.workflow.presentation.viewmodels.JourneyInfoViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class JourneyInfoFragment : Fragment() {
    private var _binding: FragmentJourneyInfoBinding? = null
    private val binding: FragmentJourneyInfoBinding get() = _binding!!

    private val args by navArgs<JourneyInfoFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[JourneyInfoViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication)
            .component
            .activityComponentFactory()
            .create(args.journeyId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentJourneyInfoBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupClickListeners()
        setupSwipeRefresh()
    }

    private fun observeViewModel() {
        viewModel.journey.observe(viewLifecycleOwner) { journey ->
            journey?.let {
                // Показываем основной контент, скрываем ошибку
                binding.contentContainer.visibility = View.VISIBLE
                binding.errorState.visibility = View.GONE

                // Управляем видимостью кнопок в зависимости от статуса
                binding.confirmJourneyButton.visibility =
                    if (journey.status == JourneyStatus.NEW) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                binding.startJourneyButton.visibility =
                    if (journey.status == JourneyStatus.CONFIRMED) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                binding.finishJourneyButton.visibility =
                    if (journey.status == JourneyStatus.STARTED) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            } ?: run {
                // Если задача null, показываем ошибку
                binding.contentContainer.visibility = View.GONE
                binding.errorState.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Управляем индикатором загрузки в SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                // Показываем ошибку в Snackbar
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                    .setAction("Повторить") { viewModel.loadData() }
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            viewModel.errorMessageShown()
                        }
                    })
                    .show()
                viewModel.errorMessageShown()
            }
        }

        viewModel.successMessage.observe(viewLifecycleOwner) { successMessage ->
            successMessage?.let {
                // Показываем успешное сообщение
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            viewModel.successMessageShown()
                        }
                    })
                    .show()
                viewModel.successMessageShown()
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                // Показываем экран ошибки, скрываем контент
                binding.contentContainer.visibility = View.GONE
                binding.errorState.visibility = View.VISIBLE
            } else {
                // Показываем контент, скрываем ошибку
                binding.contentContainer.visibility = View.VISIBLE
                binding.errorState.visibility = View.GONE
            }
        }
    }

    private fun setupClickListeners() {

        // Кнопка повтора на экране ошибки
        binding.updateErrorStateButton.setOnClickListener {
            viewModel.loadData()
        }

        // Кнопка ознакомления с выездом
        binding.confirmJourneyButton.setOnClickListener {
            viewModel.confirmJourney()
        }

        // Кнопка начала выезда
        binding.startJourneyButton.setOnClickListener {
            viewModel.startJourney()
        }

        // Кнопка завершения выезда
        binding.finishJourneyButton.setOnClickListener {
            viewModel.finishJourney()
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            // При свайпе вниз загружаем данные заново
            viewModel.loadData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}