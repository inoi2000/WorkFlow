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
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

//    private val args by navArgs<HomeFragmentArgs>()

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
        setValueToViews()
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
            with(binding.vacationsItem) {
                employee.vacations?.firstOrNull()?.let {
                    itemText.text = formatingData(it.start)
                    val status = getApprovalStatus(it.isApproval)
                    itemStatus.background = status.first
                    itemStatusText.text = status.second
                }
            }
            with(binding.businessTripsItem) {
                employee.businessTrips?.firstOrNull()?.let {
                    itemText.text = getString(R.string.business_trips_text)
                        .format(formatingData(it.start), it.place)
                    val status = getApprovalStatus(it.isApproval)
                    itemStatus.background = status.first
                    itemStatusText.text = status.second
                }
            }
            with(binding.approvalsItem) {
                employee.onApproval?.let {
                    itemText.text = getString(R.string.tasks_count).format(it.count())
                }
            }
            with(binding.tasksItem) {
                employee.tasks?.let {

                }
                binding.tasksItem.root.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToExecutingTaskListFragment()
                    )
                }
            }
        }
    }


    private fun setValueToViews() {
        with(binding) {
            with(vacationsItem) {
                itemLogo.setImageResource(R.drawable.ic_vacation)
                itemTitle.text = getString(R.string.vacation)
            }
            with(businessTripsItem) {
                itemLogo.setImageResource(R.drawable.ic_business_trip)
                itemTitle.text = getString(R.string.business_trips)
                itemStatusText.text = "Согласованно"
            }
            with(approvalsItem) {
                itemLogo.setImageResource(R.drawable.ic_approval)
                itemTitle.text = getString(R.string.on_your_approval)
            }
            with(tasksItem) {
                itemLogo.setImageResource(R.drawable.ic_task)
                itemTitle.text = getString(R.string.tasks)
                itemText.text = "Ваши цели и индивидуальный план"
            }
            with(mentoringItem) {
                itemLogo.setImageResource(R.drawable.ic_mentoring)
                itemTitle.text = getString(R.string.mentoring)
                itemText.text = "Делитесь опытом с другими сотрудниками"
            }
            with(documentsItem) {
                itemLogo.setImageResource(R.drawable.ic_document)
                itemTitle.text = getString(R.string.documents)
                itemText.text = "Необходиммо ознакомится с 8 документами"
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

        val locale = resources.configuration.getLocales().get(0)
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", locale)
        return dateFormatter.format(dateFormatter)
    }

    private fun getApprovalStatus(isApproval: Boolean): Pair<Drawable?, String> {
        return if (isApproval) {
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_green),
                getString(R.string.approval)
            )
        } else {
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_red),
                getString(R.string.not_approval)
            )
        }
    }

    private fun getTaskStatusBackground(taskStatus: TaskStatus): Pair<Drawable?, String> {
        return when (taskStatus) {
            TaskStatus.NEW -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_green),
                    getString(R.string.new_task)
                )
            }

            TaskStatus.COMPLETED -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_green),
                    getString(R.string.completed)
                )
            }

            TaskStatus.FAILED -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_red),
                    getString(R.string.not_completed)
                )
            }

            TaskStatus.IN_PROGRESS -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_blue),
                    getString(R.string.in_progress)
                )
            }

            TaskStatus.ON_APPROVAL -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_yellow),
                    getString(R.string.on_approval)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}