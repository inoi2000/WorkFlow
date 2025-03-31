package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentAbsenceListBinding
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.presentation.viewmodels.AbsenceViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.AbsenceAdapter
import javax.inject.Inject

class AbsenceListFragment : Fragment() {
    private var _binding: FragmentAbsenceListBinding? = null
    private val binding: FragmentAbsenceListBinding get() = _binding!!

    private val args by navArgs<AbsenceListFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(
            viewModelStore,
            viewModelFactory
        )[AbsenceViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentAbsenceListBinding.inflate(inflater, container, false)
        setRecyclerView()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setMode()
    }

    private fun setRecyclerView() {
        val adapter = AbsenceAdapter()
        binding.absenceListRecyclerView.itemAnimator = null
        binding.absenceListRecyclerView.adapter = adapter
        viewModel.filteredAbsenceList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setClickListeners() {
        binding.vacationsCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.VACATION)
            choseCardView(binding.vacationsCardView)
        }
        binding.businessTripsCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.BUSINESS_TRIP)
            choseCardView(binding.businessTripsCardView)
        }
        binding.sickLeavesCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.SICK_LEAVE)
            choseCardView(binding.sickLeavesCardView)
        }
        binding.daysOffCardView.setOnClickListener {
            viewModel.filteredAbsenceListByType(AbsenceType.DAY_OFF)
            choseCardView(binding.daysOffCardView)
        }
    }

    private fun setMode() {
        when (args.absenceType) {
            AbsenceType.VACATION -> binding.vacationsCardView.callOnClick()
            AbsenceType.BUSINESS_TRIP -> binding.businessTripsCardView.callOnClick()
            AbsenceType.SICK_LEAVE -> binding.sickLeavesCardView.callOnClick()
            AbsenceType.DAY_OFF -> binding.daysOffCardView.callOnClick()
        }
    }

    private fun choseCardView(cardView: CardView) {
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
        val greyColor = ContextCompat.getColor(requireContext(), R.color.grey_for_text)

        binding.daysOffCounterTextView.setTextColor(greyColor)
        binding.daysOffCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        binding.sickLeavesCounterTextView.setTextColor(greyColor)
        binding.sickLeavesCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        binding.businessTripsCounterTextView.setTextColor(greyColor)
        binding.businessTripsCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        binding.vacationsCounterTextView.setTextColor(greyColor)
        binding.vacationsCounterTextView.setBackgroundResource(R.drawable.circle_grey)

        when (cardView) {
            binding.daysOffCardView -> {
                binding.daysOffCounterTextView.setTextColor(whiteColor)
                binding.daysOffCounterTextView.setBackgroundResource(R.drawable.circle_yellow)
            }

            binding.sickLeavesCardView -> {
                binding.sickLeavesCounterTextView.setTextColor(whiteColor)
                binding.sickLeavesCounterTextView.setBackgroundResource(R.drawable.circle_red)
            }

            binding.businessTripsCardView -> {
                binding.businessTripsCounterTextView.setTextColor(whiteColor)
                binding.businessTripsCounterTextView.setBackgroundResource(R.drawable.circle_blue)
            }

            binding.vacationsCardView -> {
                binding.vacationsCounterTextView.setTextColor(whiteColor)
                binding.vacationsCounterTextView.setBackgroundResource(R.drawable.circle_green)
            }
        }
    }
}