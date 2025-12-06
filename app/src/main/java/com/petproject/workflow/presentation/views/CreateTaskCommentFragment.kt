package com.petproject.workflow.presentation.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskCommentBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskCommentViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.TaskCommentListFragment.Companion.MODE_FROM_EXECUTOR
import com.petproject.workflow.presentation.views.TaskCommentListFragment.Companion.MODE_FROM_INSPECTOR
import javax.inject.Inject

class CreateTaskCommentFragment : Fragment() {

    private var _binding: FragmentCreateTaskCommentBinding? = null
    private val binding: FragmentCreateTaskCommentBinding get() = _binding!!

    private val args by navArgs<CreateTaskCommentFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[CreateTaskCommentViewModel::class]
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
        _binding = FragmentCreateTaskCommentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.commentsCounterTextView.text = args.commentsCount
        binding.detailsButton.setOnClickListener {
            val action = when (args.modeForm) {
                MODE_FROM_INSPECTOR -> {
                    CreateTaskCommentFragmentDirections
                        .actionCreateTaskCommentFragmentToInspectorTaskInfoFragment(args.taskId)
                }

                MODE_FROM_EXECUTOR -> {
                    CreateTaskCommentFragmentDirections
                        .actionCreateTaskCommentFragmentToExecutingTaskInfoFragment(args.taskId)
                }

                else -> {
                    throw RuntimeException()
                }
            }
            findNavController().navigate(action)
        }
        binding.createNewCommentButton.setOnClickListener {
            val commentText = binding.etText.text.toString()
            viewModel.createComment(commentText)
        }
        binding.uploadFileButton.setOnClickListener {
            // Открываем файловый менеджер для выбора файла
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*" // Все типы файлов
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            try {
                startActivityForResult(
                    Intent.createChooser(intent, "Выберите файл"),
                    REQUEST_CODE_PICK_FILE
                )
            } catch (ex: android.content.ActivityNotFoundException) {
                Toast.makeText(requireContext(), "Установите файловый менеджер", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let {
                // Вызываем метод ViewModel для загрузки файла
                viewModel.uploadFile(it)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isCommentCreated.observe(viewLifecycleOwner) {
            if (it) {
                val action = CreateTaskCommentFragmentDirections
                    .actionCreateTaskCommentFragmentToTaskCommentListFragment(
                        args.taskId,
                        args.modeForm
                    )
                findNavController().navigate(action)
            }
        }

        // Наблюдаем за состоянием загрузки файла
        viewModel.uploadState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CreateTaskCommentViewModel.FileUploadState.Loading -> {
                    // Показываем индикатор загрузки
                    binding.uploadFileButton.isEnabled = false
                    binding.uploadFileButton.text = "Загрузка..."
                }

                is CreateTaskCommentViewModel.FileUploadState.Success -> {
                    // Возвращаем кнопку в исходное состояние
                    binding.uploadFileButton.isEnabled = true
                    binding.uploadFileButton.text = "Прикрепить файл"

                    // Показываем успешное сообщение
                    Toast.makeText(
                        requireContext(),
                        "Файл успешно загружен. Ключ: ${state.fileKey}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Можно автоматически добавить fileKey в текст комментария
                    val currentText = binding.etText.text.toString()
                    if (currentText.isNotEmpty()) {
                        binding.etText.setText("$currentText\n[Файл: ${state.fileKey}]")
                    } else {
                        binding.etText.setText("[Файл: ${state.fileKey}]")
                    }
                }

                is CreateTaskCommentViewModel.FileUploadState.Error -> {
                    // Возвращаем кнопку в исходное состояние
                    binding.uploadFileButton.isEnabled = true
                    binding.uploadFileButton.text = "Прикрепить файл"

                    // Показываем ошибку
                    Toast.makeText(
                        requireContext(),
                        "Ошибка загрузки: ${state.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {
                    // Idle состояние - ничего не делаем
                    binding.uploadFileButton.isEnabled = true
                    binding.uploadFileButton.text = "Прикрепить файл"
                }
            }
        }

        // Можно также наблюдать за загруженными ключами файлов
        viewModel.uploadedFileKeys.observe(viewLifecycleOwner) { fileKey ->
            fileKey?.let {
                // Можно обновить UI, если нужно показать список загруженных файлов
                // Например, добавить badge с количеством загруженных файлов
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_FILE = 1002
    }
}