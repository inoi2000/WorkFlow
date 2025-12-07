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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskCommentBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskCommentViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.UploadedFilesAdapter
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

    private lateinit var uploadedFilesAdapter: UploadedFilesAdapter

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

        setupRecyclerView()
        setupClickListeners()
        setupObservers()

        binding.commentsCounterTextView.text = args.commentsCount.toString()
    }

    private fun setupRecyclerView() {
        uploadedFilesAdapter = UploadedFilesAdapter { fileKey ->
            showDeleteFileConfirmation(fileKey)
        }

        binding.rvUploadedFiles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = uploadedFilesAdapter
//            setHasFixedSize(true)
        }
    }

    private fun setupClickListeners() {
        binding.detailsButton.setOnClickListener {
            navigateToTaskDetails()
        }

        binding.btnCreateComment.setOnClickListener {
            val commentText = binding.etText.text.toString().trim()
            viewModel.createComment(commentText)
        }

        binding.btnAttachFile.setOnClickListener {
            openFilePicker()
        }
    }

    private fun setupObservers() {
        viewModel.isCommentCreated.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) {
                showSuccessMessage()
                navigateBackToComments()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnCreateComment.isEnabled = false
                binding.btnAttachFile.isEnabled = false
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnCreateComment.isEnabled = true
                binding.btnAttachFile.isEnabled = true
            }
        }

        viewModel.uploadState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CreateTaskCommentViewModel.FileUploadState.Loading -> {
                    binding.btnAttachFile.apply {
                        isEnabled = false
                        text = "Загрузка..."
                    }
                }
                is CreateTaskCommentViewModel.FileUploadState.Success -> {
                    binding.btnAttachFile.apply {
                        isEnabled = true
                        text = "Прикрепить файл"
                    }

                    // Прокручиваем к новому файлу
                    binding.scrollView.postDelayed({
                        binding.scrollView.fullScroll(View.FOCUS_DOWN)
                    }, 100)
                }
                else -> {
                    binding.btnAttachFile.apply {
                        isEnabled = true
                        text = "Прикрепить файл"
                    }
                }
            }
        }

        viewModel.uploadedFileKeys.observe(viewLifecycleOwner) { files ->
            uploadedFilesAdapter.submitList(files)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                showErrorSnackbar(it)
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        try {
            startActivityForResult(
                Intent.createChooser(intent, "Выберите файл"),
                REQUEST_CODE_PICK_FILE
            )
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Установите файловый менеджер", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.uploadFile(uri)
            }
        }
    }

    private fun showDeleteFileConfirmation(fileKey: com.petproject.workflow.domain.entities.FileKey) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удалить файл?")
            .setMessage("Вы уверены, что хотите удалить файл \"${fileKey.key}\"?")
            .setPositiveButton("Удалить") { _, _ ->
                viewModel.deleteFile(fileKey)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showSuccessMessage() {
        Snackbar.make(
            binding.root,
            "Комментарий успешно создан",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToTaskDetails() {
        val action = when (args.modeForm) {
            TaskCommentListFragment.MODE_FROM_INSPECTOR -> {
                CreateTaskCommentFragmentDirections
                    .actionCreateTaskCommentFragmentToInspectorTaskInfoFragment(args.taskId)
            }
            TaskCommentListFragment.MODE_FROM_EXECUTOR -> {
                CreateTaskCommentFragmentDirections
                    .actionCreateTaskCommentFragmentToExecutingTaskInfoFragment(args.taskId)
            }
            else -> throw IllegalStateException("Неизвестный режим")
        }
        findNavController().navigate(action)
    }

    private fun navigateBackToComments() {
        val action = CreateTaskCommentFragmentDirections
            .actionCreateTaskCommentFragmentToTaskCommentListFragment(
                args.taskId,
                args.modeForm
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PICK_FILE = 1002
    }
}