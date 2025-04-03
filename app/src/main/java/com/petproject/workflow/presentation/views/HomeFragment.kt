package com.petproject.workflow.presentation.views

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
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
import com.petproject.workflow.databinding.FragmentHomeBinding
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.AbsenceInfoViewHolder
import com.petproject.workflow.presentation.views.adapters.TaskInfoViewHolder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[HomeViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication)
            .component
            .activityComponentFactory()
            .create((requireActivity() as MainActivity).employeeId)
    }

    private val employee by lazy {
        viewModel.employee
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.navigateToLoginScreen.observe(viewLifecycleOwner) {
            if (it) {
                val intent = LoginActivity.newIntent(requireContext())
                startActivity(intent)
                requireActivity().finish()
            }
        }
        employee.observe(viewLifecycleOwner) { employee ->

        }
        viewModel.vacation.observe(viewLifecycleOwner) { vacation ->
            val absenceInfoViewHolder = AbsenceInfoViewHolder(binding.vacationItem)
            if (vacation != null) {
                absenceInfoViewHolder.bind(vacation)
            } else {
                absenceInfoViewHolder.binding.root.visibility = View.GONE
            }
        }
        viewModel.businessTrip.observe(viewLifecycleOwner) { businessTrip ->
            val absenceInfoViewHolder = AbsenceInfoViewHolder(binding.businessTripItem)
            if (businessTrip != null) {
                absenceInfoViewHolder.bind(businessTrip)
            } else {
                absenceInfoViewHolder.binding.root.visibility = View.GONE
            }
        }
        viewModel.executorTask.observe(viewLifecycleOwner) { task ->
            val taskInfoViewHolder = TaskInfoViewHolder(binding.executorTask)
            if (task != null) {
                taskInfoViewHolder.bind(
                    task,
                    TaskInfoViewHolder.EXECUTOR_MODE,
                    {},
                    {}
                )
            } else {
                taskInfoViewHolder.binding.root.visibility = View.GONE
            }
        }
        viewModel.inspectorTask.observe(viewLifecycleOwner) { task ->
            val taskInfoViewHolder = TaskInfoViewHolder(binding.onApprovalTask)
            if (task != null) {
                taskInfoViewHolder.bind(
                    task,
                    TaskInfoViewHolder.EXECUTOR_MODE,
                    {},
                    {}
                )
            } else {
                taskInfoViewHolder.binding.root.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatingData(data: LocalDate): String {
//        val dateParser = SimpleDateFormat("dd.MM.yyyy")
//        val date = dateParser.parse(data) ?: return data
//        val locale = resources.configuration.getLocales().get(0)
//        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", locale)
//        return dateFormatter.format(date)

//        val locale = resources.configuration.getLocales().get(0)
//        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", locale)
//        return dateFormatter.format(dateFormatter)

        return data.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}