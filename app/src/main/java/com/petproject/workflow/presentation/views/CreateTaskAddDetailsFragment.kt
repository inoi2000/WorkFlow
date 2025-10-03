package com.petproject.workflow.presentation.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskAddDetailsBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskAddDetailsViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeInfoViewHolder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CreateTaskAddDetailsFragment : Fragment() {
    private var _binding: FragmentCreateTaskAddDetailsBinding? = null
    private val binding: FragmentCreateTaskAddDetailsBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args by navArgs<CreateTaskAddDetailsFragmentArgs>()
    private lateinit var viewModel: CreateTaskAddDetailsViewModel

    private val calendar = Calendar.getInstance()

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateTaskAddDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[CreateTaskAddDetailsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupViews() {
        setupEmployeeInfo()
        setupTextFields()
        setupSwitches()
        setupDatePicker()
        setupCreateButton()
    }

    private fun setupEmployeeInfo() {
        val executorEmployee = EmployeeInfoViewHolder(binding.executorEmployee)
        executorEmployee.bind(args.employee) {}
    }

    private fun setupTextFields() {
        // Валидация описания в реальном времени
        binding.etDescription.doAfterTextChanged { text ->
            if (!text.isNullOrBlank()) {
                binding.tilDescription.error = null
            }
        }

        // Валидация дедлайна
        binding.etDeadline.doAfterTextChanged { text ->
            if (!text.isNullOrBlank()) {
                binding.tilDeadline.error = null
            }
        }
    }

    private fun setupSwitches() {
        binding.prioritySwitchMaterial.setOnCheckedChangeListener { _, isChecked ->
            viewModel.priorityField = isChecked
            if (isChecked) {
                showPriorityWarning()
            }
        }

        binding.shouldBeInspectedSwitchMaterial.setOnCheckedChangeListener { _, isChecked ->
            viewModel.shouldBeInspectedField = isChecked
        }
    }

    private fun setupDatePicker() {
        binding.etDeadline.setOnClickListener {
            showDatePickerDialog()
        }

        binding.tilDeadline.setEndIconOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = java.text.SimpleDateFormat(viewModel.dateFormatPattern, Locale.getDefault())
                binding.etDeadline.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Установка минимальной даты - сегодня
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datePickerDialog.show()
    }

    private fun setupCreateButton() {
        binding.createTaskButton.setOnClickListener {
            if (validateForm()) {
                viewModel.createTask(args.employee)
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (binding.etDescription.text.isNullOrBlank()) {
            binding.tilDescription.error = "Введите описание задачи"
            isValid = false
        }

        if (binding.etDeadline.text.isNullOrBlank()) {
            binding.tilDeadline.error = "Укажите дедлайн"
            isValid = false
        }

        return isValid
    }

    private fun showPriorityWarning() {
        Snackbar.make(binding.root, "Срочная задача будет выделена в списке", Snackbar.LENGTH_LONG)
            .setAction("OK") { }
            .show()
    }

    private fun setupObservers() {
        viewModel.navigateToDoneCreateTaskScreen.observe(viewLifecycleOwner) { task ->
            task?.let {
                navigateToNextScreen(it)
            }
        }

        viewModel.errorInputDescription.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                binding.tilDescription.error = "Введите описание задачи"
            }
        }

        viewModel.errorInputDeadline.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                binding.tilDeadline.error = "Укажите корректный дедлайн"
            }
        }
    }

    private fun navigateToNextScreen(task: com.petproject.workflow.domain.entities.Task) {
        val action = CreateTaskAddDetailsFragmentDirections
            .actionCreateTaskAddDetailsFragmentToCreateTaskDoneFragment(task)
        findNavController().navigate(action)
        viewModel.onDoneCreateTaskScreenNavigated()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}