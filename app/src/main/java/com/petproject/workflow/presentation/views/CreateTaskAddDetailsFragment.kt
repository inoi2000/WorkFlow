package com.petproject.workflow.presentation.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskAddDetailsBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskAddDetailsViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CreateTaskAddDetailsFragment : Fragment() {
    private var _binding: FragmentCreateTaskAddDetailsBinding? = null
    private val binding: FragmentCreateTaskAddDetailsBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val calendar = Calendar.getInstance()

    private val args by navArgs<CreateTaskAddDetailsFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[CreateTaskAddDetailsViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateTaskAddDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.etDeadline.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val format = viewModel.dateFormatPattern
                    val dateFormat = SimpleDateFormat(format, Locale.US)
                    binding.etDeadline.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.createTaskButton.setOnClickListener {
            viewModel.resetErrorInputDeadline()
            viewModel.resetErrorInputDescription()
            viewModel.createTask(args.employee)
        }
    }

    private fun observeViewModel() {
        viewModel.navigateToDoneCreateTaskScreen.observe(viewLifecycleOwner) { task ->
            task?.let {
                val action = CreateTaskAddDetailsFragmentDirections
                    .actionCreateTaskAddDetailsFragmentToCreateTaskDoneFragment(task)
                findNavController().navigate(action)
                viewModel.onDoneCreateTaskScreenNavigated()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}