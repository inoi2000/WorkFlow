package com.petproject.workflow.presentation.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateStatementBinding
import com.petproject.workflow.domain.entities.CarStatus
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.presentation.utils.SelectionCarArg
import com.petproject.workflow.presentation.utils.SelectionEmployeeArg
import com.petproject.workflow.presentation.utils.SelectionTrailerArg
import com.petproject.workflow.presentation.viewmodels.CreateStatementViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.CarInfoViewHolder
import com.petproject.workflow.presentation.views.adapters.EmployeeInfoViewHolder
import com.petproject.workflow.presentation.views.adapters.TrailerInfoViewHolder
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CreateStatementFragment : Fragment() {
    private var _binding: FragmentCreateStatementBinding? = null
    private val binding: FragmentCreateStatementBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[CreateStatementViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateStatementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSelectedCarCard()
        setupSelectedTrailerCard()
        setupSelectedEmployeeCard()

        setupDatePicker()
        setupTimePicker()
    }

    private val calendar = Calendar.getInstance()

    private fun setupDatePicker() {
        binding.etDestinationDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.tilDestinationDate.setEndIconOnClickListener {
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
                binding.etDestinationDate.setText(dateFormat.format(calendar.time))

                setProgressBarStatus(ProgressBarStatus.WRITING_DETAILS)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Установка минимальной даты - сегодня
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datePickerDialog.show()
    }

    private fun setupTimePicker() {
        binding.etDestinationTime.setOnClickListener {
            showTimePickerDialog()
        }

        binding.tilDestinationTime.setEndIconOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                binding.etDestinationTime.setText("%02d:%02d".format(hour, minute))

                setProgressBarStatus(ProgressBarStatus.WRITING_DETAILS)
            },
            LocalTime.now().hour,
            LocalTime.now().minute,
            true
        )
        timePickerDialog.show()
    }

    private fun setupSelectedCarCard() {
        binding.btnAddCar.setOnClickListener {
            selectCar()
        }

        binding.btnRemoveCar.setOnClickListener {
            viewModel.removeCar()
        }

        viewModel.car.observe(viewLifecycleOwner) { car ->
            if (car != null) {
                val carVH = CarInfoViewHolder(
                    binding.carInfo,
                ) { }
                carVH.bind(car)

                binding.btnAddCar.visibility = View.GONE
                binding.btnRemoveCar.visibility = View.VISIBLE
                binding.carInfo.root.visibility = View.VISIBLE

                setProgressBarStatus(ProgressBarStatus.SELECTING_CAR)
            } else {
                binding.btnAddCar.visibility = View.VISIBLE
                binding.btnRemoveCar.visibility = View.GONE
                binding.carInfo.root.visibility = View.GONE
            }
        }
    }

    private fun selectCar() {
        val action = CreateStatementFragmentDirections
            .actionCreateStatementFragmentToSelectionCarFragment(
                SelectionCarArg(
                    {
                        viewModel.getAllCarsByStatusUseCase.invoke(CarStatus.ACTIVE)
                    },
                    { car ->
                        viewModel.setCar(car)
                        findNavController().navigateUp()
                    }
                )
            )
        findNavController().navigate(action)
    }


    private fun setupSelectedTrailerCard() {
        binding.btnAddTrailer.setOnClickListener {
            selectTrailer()
        }

        binding.btnRemoveTrailer.setOnClickListener {
            viewModel.removeTrailer()
        }

        viewModel.trailer.observe(viewLifecycleOwner) { trailer ->
            if (trailer != null) {
                val trailerVH = TrailerInfoViewHolder(
                    binding.trailerInfo
                ) { }
                trailerVH.bind(trailer)

                binding.btnAddTrailer.visibility = View.GONE
                binding.btnRemoveTrailer.visibility = View.VISIBLE
                binding.trailerInfo.root.visibility = View.VISIBLE

                setProgressBarStatus(ProgressBarStatus.SELECTING_TRAILER)
            } else {
                binding.btnAddTrailer.visibility = View.VISIBLE
                binding.btnRemoveTrailer.visibility = View.GONE
                binding.trailerInfo.root.visibility = View.GONE
            }
        }
    }

    private fun selectTrailer() {
        val action = CreateStatementFragmentDirections
            .actionCreateStatementFragmentToSelectionTrailerFragment(
                SelectionTrailerArg { trailer ->
                    viewModel.setTrailer(trailer)
                    findNavController().navigateUp()
                }
            )
        findNavController().navigate(action)
    }


    private fun setupSelectedEmployeeCard() {
        binding.btnAddEmployee.setOnClickListener {
            selectEmployee()
        }

        binding.btnRemoveEmployee.setOnClickListener {
            viewModel.removeDriver()
        }

        viewModel.driver.observe(viewLifecycleOwner) { employee ->
            if (employee != null) {
                val driverVH = EmployeeInfoViewHolder(
                    binding.employeeInfo,
                    viewModel.requestManager)
                driverVH.bind(employee) { }

                binding.btnAddEmployee.visibility = View.GONE
                binding.btnRemoveEmployee.visibility = View.VISIBLE
                binding.employeeInfo.root.visibility = View.VISIBLE

                setProgressBarStatus(ProgressBarStatus.SELECTING_EMPLOYEE)
            } else {
                binding.btnAddEmployee.visibility = View.VISIBLE
                binding.btnRemoveEmployee.visibility = View.GONE
                binding.employeeInfo.root.visibility = View.GONE
            }
        }
    }

    private fun selectEmployee() {
        val action = CreateStatementFragmentDirections
            .actionCreateStatementFragmentToSelectionEmployeeFragment(
                SelectionEmployeeArg(
                    {
                        viewModel.getAllDriverEmployeesUseCase.invoke()
                    },
                    { employee: Employee ->
                        viewModel.setDriver(employee)
                        findNavController().navigateUp()
                    }
                )
            )
        findNavController().navigate(action)
    }

    private enum class ProgressBarStatus {
        WRITING_DETAILS,
        SELECTING_CAR,
        SELECTING_TRAILER,
        SELECTING_EMPLOYEE,
        DONE
    }

    private fun setProgressBarStatus(status: ProgressBarStatus) {
        val bgProgressDotActive = R.drawable.bg_progress_dot_active
        val bgProgressDotInactive = R.drawable.bg_progress_dot_inactive
        val textColorActive = ContextCompat.getColor(this.requireContext(), R.color.white)
        val textColorInactive = ContextCompat.getColor(this.requireContext(), R.color.white_transparent)
        val bgProgressLineActive = R.drawable.bg_progress_line_active
        val bgProgressLineInactive = R.drawable.bg_progress_line_inactive

        when (status) {
            ProgressBarStatus.WRITING_DETAILS -> {
                binding.stepTextView.text = "Шаг 1 из 5: Заполнение данных"

                binding.step1Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step1Text.setTextColor(textColorActive)
                binding.line1.setBackgroundResource(bgProgressLineActive)

                binding.step2Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step2Text.setTextColor(textColorInactive)
                binding.line2.setBackgroundResource(bgProgressLineInactive)

                binding.step3Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step3Text.setTextColor(textColorInactive)
                binding.line3.setBackgroundResource(bgProgressLineInactive)

                binding.step4Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step4Text.setTextColor(textColorInactive)
                binding.line4.setBackgroundResource(bgProgressLineInactive)

                binding.step5Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step5Text.setTextColor(textColorInactive)
            }

            ProgressBarStatus.SELECTING_CAR -> {
                binding.stepTextView.text = "Шаг 2 из 5: Выбор машины"

                binding.step1Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step1Text.setTextColor(textColorActive)
                binding.line1.setBackgroundResource(bgProgressLineActive)

                binding.step2Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step2Text.setTextColor(textColorActive)
                binding.line2.setBackgroundResource(bgProgressLineActive)

                binding.step3Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step3Text.setTextColor(textColorInactive)
                binding.line3.setBackgroundResource(bgProgressLineInactive)

                binding.step4Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step4Text.setTextColor(textColorInactive)
                binding.line4.setBackgroundResource(bgProgressLineInactive)

                binding.step5Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step5Text.setTextColor(textColorInactive)
            }

            ProgressBarStatus.SELECTING_TRAILER -> {
                binding.stepTextView.text = "Шаг 3 из 5: Выбор прицепа"

                binding.step1Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step1Text.setTextColor(textColorActive)
                binding.line1.setBackgroundResource(bgProgressLineActive)

                binding.step2Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step2Text.setTextColor(textColorActive)
                binding.line2.setBackgroundResource(bgProgressLineActive)

                binding.step3Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step3Text.setTextColor(textColorActive)
                binding.line3.setBackgroundResource(bgProgressLineActive)

                binding.step4Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step4Text.setTextColor(textColorInactive)
                binding.line4.setBackgroundResource(bgProgressLineInactive)

                binding.step5Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step5Text.setTextColor(textColorInactive)
            }

            ProgressBarStatus.SELECTING_EMPLOYEE -> {
                binding.stepTextView.text = "Шаг 4 из 5: Выбор водителя"

                binding.step1Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step1Text.setTextColor(textColorActive)
                binding.line1.setBackgroundResource(bgProgressLineActive)

                binding.step2Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step2Text.setTextColor(textColorActive)
                binding.line2.setBackgroundResource(bgProgressLineActive)

                binding.step3Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step3Text.setTextColor(textColorActive)
                binding.line3.setBackgroundResource(bgProgressLineActive)

                binding.step4Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step4Text.setTextColor(textColorActive)
                binding.line4.setBackgroundResource(bgProgressLineActive)

                binding.step5Indicator.setBackgroundResource(bgProgressDotInactive)
                binding.step5Text.setTextColor(textColorInactive)
            }

            ProgressBarStatus.DONE -> {
                binding.stepTextView.text = "Шаг 5 из 5: Готово"

                binding.step1Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step1Text.setTextColor(textColorActive)
                binding.line1.setBackgroundResource(bgProgressLineActive)

                binding.step2Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step2Text.setTextColor(textColorActive)
                binding.line2.setBackgroundResource(bgProgressLineActive)

                binding.step3Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step3Text.setTextColor(textColorActive)
                binding.line3.setBackgroundResource(bgProgressLineActive)

                binding.step4Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step4Text.setTextColor(textColorActive)
                binding.line4.setBackgroundResource(bgProgressLineActive)

                binding.step5Indicator.setBackgroundResource(bgProgressDotActive)
                binding.step5Text.setTextColor(textColorActive)
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}