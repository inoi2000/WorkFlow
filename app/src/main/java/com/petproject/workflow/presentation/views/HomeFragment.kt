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
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.R
import com.petproject.workflow.databinding.FragmentHomeBinding
import com.petproject.workflow.domain.entities.Status
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
import com.petproject.workflow.presentation.viewmodels.HomeViewModelFactory
import java.text.SimpleDateFormat

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

//    private val args by navArgs<HomeFragmentArgs>()

    private val viewModelFactory by lazy {
        HomeViewModelFactory((requireActivity() as MainActivity).employeeId)
    }

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[HomeViewModel::class]
    }

    private val employee by lazy {
        viewModel.employee
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setValueToViews()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        employee.observe(viewLifecycleOwner) { employee ->
            with(binding.vacationsItem) {
                employee.vacations.firstOrNull()?.let {
                    itemText.text = formatingData(it.start)
                    val status = getApprovalStatus(it.isApproval)
                    itemStatus.background = status.first
                    itemStatusText.text = status.second
                }
            }
            with(binding.businessTripsItem) {
                employee.businessTrips.firstOrNull()?.let {
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
    private fun formatingData(data: String): String {
        val dateParser = SimpleDateFormat("dd.MM.yyyy")
        val date = dateParser.parse(data) ?: return data
        val locale = resources.configuration.getLocales().get(0)
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", locale)
        return dateFormatter.format(date)
    }

    private fun getApprovalStatus(isApproval: Boolean): Pair<Drawable?, String> {
        return if (isApproval) {
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_green),
                getString(R.string.approval)
            )
        }  else {
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_red),
                getString(R.string.not_approval)
            )
        }
    }

    private fun getTaskStatusBackground(status: Status): Pair<Drawable?, String> {
        return when (status) {
            Status.COMPLETED -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_green),
                    getString(R.string.completed)
                )
            }
            Status.NOT_COMPLETED -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_red),
                    getString(R.string.not_completed)
                )
            }
            Status.IN_PROGRESS -> {
                Pair(
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_blue),
                    getString(R.string.in_progress)
                )
            }
            Status.ON_APPROVAL -> {
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