package com.petproject.workflow.presentation.views

import android.annotation.SuppressLint
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
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
import com.petproject.workflow.presentation.viewmodels.HomeViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val args by navArgs<HomeFragmentArgs>()

    private val viewModelFactory by lazy {
        HomeViewModelFactory(args.employeeId)
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
                }
            }
            with(binding.businessTripsItem) {
                employee.businessTrips.firstOrNull()?.let {
                    itemText.text = "${formatingData(it.start)}, ${it.place}"
                }
            }
            with(binding.approvalsItem) {
                employee.onApproval?.let {
                    itemText.text = "${it.count()} задач(а)"
                }
            }
            with(binding.tasksItem) {

            }
        }
    }


    private fun setValueToViews() {
        employee.observe(viewLifecycleOwner) { employee ->
            with(binding) {
                employee.vacations.isEmpty()
                with(vacationsItem) {
                    itemLogo.setImageResource(R.drawable.ic_vacation)
                    itemTitle.text = getString(R.string.vacation)
//                    itemStatus.background =
//                        ContextCompat.getDrawable(requireContext(), R.drawable.circle_green)
//                    itemStatusText.text = "Согласованно"
                }
                with(businessTripsItem) {
                    itemLogo.setImageResource(R.drawable.ic_business_trip)
                    itemTitle.text = getString(R.string.business_trips)
                    itemStatus.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.circle_green)
                    itemStatusText.text = "Согласованно"
                }
                with(approvalsItem) {
                    itemLogo.setImageResource(R.drawable.ic_approval)
                    itemTitle.text = getString(R.string.on_your_approval)
//                    itemStatus.background =
//                        ContextCompat.getDrawable(requireContext(), R.drawable.circle_red)
//                    itemStatusText.text = "Не согласованно"
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
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatingData(data: String): String {
        val dateParser = SimpleDateFormat("dd.MM.yyyy")
        val date = dateParser.parse(data) ?: return data
        val locale = resources.configuration.getLocales().get(0)
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", locale)
        return dateFormatter.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}