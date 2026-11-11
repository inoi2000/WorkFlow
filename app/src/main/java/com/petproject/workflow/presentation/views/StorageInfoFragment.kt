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
import com.petproject.workflow.databinding.FragmentStorageInfoBinding
import com.petproject.workflow.presentation.viewmodels.StorageInfoViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class StorageInfoFragment : Fragment() {
    private var _binding: FragmentStorageInfoBinding? = null
    private val binding: FragmentStorageInfoBinding get() = _binding!!

    private val args by navArgs<StorageInfoFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[StorageInfoViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication)
            .component
            .activityComponentFactory()
            .create(args.storageId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentStorageInfoBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupClickListeners()
        setupSwipeRefresh()
        setupActionButtons()
    }

    private fun observeViewModel() {
        viewModel.storage.observe(viewLifecycleOwner) { storage ->
            storage?.let {
                // Показываем основной контент, скрываем ошибку
                binding.contentContainer.visibility = View.VISIBLE
                binding.errorState.visibility = View.GONE
            } ?: run {
                // Если storage null, показываем ошибку
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

        // Отслеживаем видимость кнопок для обновления UI
        viewModel.showPourButton.observe(viewLifecycleOwner) { showPour ->
            binding.pourButton.visibility = if (showPour) View.VISIBLE else View.GONE
        }

        viewModel.showDrainButton.observe(viewLifecycleOwner) { showDrain ->
            binding.drainButton.visibility = if (showDrain) View.VISIBLE else View.GONE
        }

        viewModel.showCheckButton.observe(viewLifecycleOwner) { showCheck ->
            binding.checkButton.visibility = if (showCheck) View.VISIBLE else View.GONE
        }
    }

    private fun setupClickListeners() {
        // Кнопка повтора на экране ошибки
        binding.updateErrorStateButton.setOnClickListener {
            viewModel.loadData()
        }

        // Кнопка навигации назад на тулбаре
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            // При свайпе вниз загружаем данные заново
            viewModel.loadData()
        }
    }

    private fun setupActionButtons() {
        // Кнопка заливки - здесь можно показать диалог для ввода объема и выбора продукта
//        binding.pourButton.setOnClickListener {
//            showPourDialog()
//        }
//
//        // Кнопка слива - показать диалог для ввода объема
//        binding.drainButton.setOnClickListener {
//            showDrainDialog()
//        }
//
//        // Кнопка проверки
//        binding.checkButton.setOnClickListener {
//            viewModel.checkStorage()
//        }
    }

    private fun showPourDialog() {
        val currentStorage = viewModel.storage.value ?: return

        // Создаем диалог для заливки
        val products = currentStorage.allowedProducts ?: emptyList()
        if (products.isEmpty()) {
            Snackbar.make(binding.root, "Нет доступных продуктов для заливки", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Здесь можно реализовать кастомный диалог или использовать AlertDialog
        // Для простоты показываем Snackbar с запросом
        Snackbar.make(
            binding.root,
            "Заливка: доступно ${String.format("%.1f", currentStorage.totalVolume - currentStorage.currentVolume)} м³",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Залить") {
                // Пример: заливаем 1 м³ первым продуктом
                val amount = 1.0
                val productId = products.firstOrNull()?.id ?: return@setAction
                if (amount <= currentStorage.totalVolume - currentStorage.currentVolume) {
                    viewModel.pourStorage(amount, productId)
                } else {
                    Snackbar.make(binding.root, "Превышен допустимый объем", Snackbar.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun showDrainDialog() {
        val currentStorage = viewModel.storage.value ?: return

        Snackbar.make(
            binding.root,
            "Слив: доступно ${String.format("%.1f", currentStorage.currentVolume)} м³",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Слить") {
                // Пример: сливаем 1 м³
                val amount = 1.0
                if (amount <= currentStorage.currentVolume) {
                    viewModel.drainStorage(amount)
                } else {
                    Snackbar.make(binding.root, "Недостаточно продукта для слива", Snackbar.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}