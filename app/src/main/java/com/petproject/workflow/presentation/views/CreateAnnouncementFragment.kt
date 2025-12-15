package com.petproject.workflow.presentation.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateAnnouncementBinding
import com.petproject.workflow.presentation.viewmodels.CreateAnnouncementViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateAnnouncementFragment : Fragment() {
    private var _binding: FragmentCreateAnnouncementBinding? = null
    private val binding: FragmentCreateAnnouncementBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[CreateAnnouncementViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadFile(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateAnnouncementBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener {
            if (hasUnsavedChanges()) {
                showUnsavedChangesDialog()
            } else {
                findNavController().navigateUp()
            }
        }

        binding.attachFileButton.setOnClickListener {
            openFilePicker()
        }

        binding.deleteAttachmentButton.setOnClickListener {
            showDeleteAttachmentDialog()
        }

        binding.createButton.setOnClickListener {
            viewModel.createAnnouncement()
        }
    }

    private fun setupObservers() {
        viewModel.isAnnouncementCreated.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) {
                Toast.makeText(
                    requireContext(),
                    "Объявление успешно создано",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
        }

        viewModel.uploadState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CreateAnnouncementViewModel.FileUploadState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Файл успешно загружен",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is CreateAnnouncementViewModel.FileUploadState.Error -> {
                    // Error already shown via errorMessage
                }
                else -> {}
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it.isNotBlank()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun openFilePicker() {
        filePickerLauncher.launch("*/*")
    }

    private fun showDeleteAttachmentDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удалить файл")
            .setMessage("Вы уверены, что хотите удалить прикрепленный файл?")
            .setPositiveButton("Удалить") { _, _ ->
                viewModel.uploadedFileKey.value?.let { fileKey ->
                    viewModel.deleteFile(fileKey)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun hasUnsavedChanges(): Boolean {
        val title = binding.titleEditText.text?.toString()?.trim() ?: ""
        val content = binding.contentEditText.text?.toString()?.trim() ?: ""
        return title.isNotEmpty() || content.isNotEmpty() || viewModel.hasAttachedFile.get()
    }

    private fun showUnsavedChangesDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Есть несохраненные изменения")
            .setMessage("Вы уверены, что хотите выйти? Все несохраненные изменения будут потеряны.")
            .setPositiveButton("Выйти") { _, _ ->
                findNavController().navigateUp()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}