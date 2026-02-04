package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentInspectorTaskInfoBinding
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.InspectorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeInfoViewHolder
import javax.inject.Inject

class InspectorTaskInfoFragment : Fragment() {
    private var _binding: FragmentInspectorTaskInfoBinding? = null
    private val binding: FragmentInspectorTaskInfoBinding get() = _binding!!

    private val args by navArgs<InspectorTaskInfoFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[InspectorTaskInfoViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication)
            .component
            .activityComponentFactory()
            .create(args.taskId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentInspectorTaskInfoBinding.inflate(inflater, container, false)
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
        viewModel.inspectorTask.observe(viewLifecycleOwner) { task ->
            task?.let {
                // Показываем основной контент, скрываем ошибку
                binding.contentContainer.visibility = View.VISIBLE
                binding.errorState.visibility = View.GONE

                // Биндим исполнителя
                task.executor?.let { executor ->
                    val employeeInfoViewHolder = EmployeeInfoViewHolder(
                        binding.taskExecutor,
                        viewModel.requestManager
                    )
                    employeeInfoViewHolder.bind(executor) {}
                }

                // Управляем видимостью кнопок в зависимости от статуса
                binding.approvalTaskButton.visibility =
                    if (task.status == TaskStatus.ON_APPROVAL) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                binding.rejectTaskButton.visibility =
                    if (task.status == TaskStatus.ON_APPROVAL) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                binding.cancelTaskButton.visibility =
                    if (task.status != TaskStatus.COMPLETED && task.status != TaskStatus.CANCELED) {
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
        // Клик по карточке комментариев
        binding.commentsCardView.setOnClickListener {
            val action = InspectorTaskInfoFragmentDirections
                .actionInspectorTaskInfoFragmentToTaskCommentListFragment(
                    args.taskId,
                    TaskCommentListFragment.MODE_FROM_INSPECTOR
                )
            findNavController().navigate(action)
        }

        // Кнопка повтора на экране ошибки
        binding.updateErrorStateButton.setOnClickListener {
            viewModel.loadData()
        }

        // Кнопка одобрения задачи
        binding.approvalTaskButton.setOnClickListener {
            viewModel.approvalTask()
        }

        // Кнопка отклонения задачи
        binding.rejectTaskButton.setOnClickListener {
            viewModel.rejectTask()
        }

        // Кнопка отмены задачи
        binding.cancelTaskButton.setOnClickListener {
            viewModel.cancelTask()
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