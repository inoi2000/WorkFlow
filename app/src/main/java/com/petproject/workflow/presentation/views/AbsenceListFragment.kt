package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
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
            binding.titleTextView.text = getString(R.string.vacations)
            viewModel.filteredAbsenceListByType(AbsenceType.VACATION)
            choseCardView(binding.vacationsCardView)
        }
        binding.businessTripsCardView.setOnClickListener {
            binding.titleTextView.text = getString(R.string.business_trips)
            viewModel.filteredAbsenceListByType(AbsenceType.BUSINESS_TRIP)
            choseCardView(binding.businessTripsCardView)
        }
        binding.sickLeavesCardView.setOnClickListener {
            binding.titleTextView.text = getString(R.string.sick_leaves)
            viewModel.filteredAbsenceListByType(AbsenceType.SICK_LEAVE)
            choseCardView(binding.sickLeavesCardView)
        }
        binding.daysOffCardView.setOnClickListener {
            binding.titleTextView.text = getString(R.string.days_off)
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
        binding.vacationsSelector.visibility = View.GONE
        binding.businessTripsSelector.visibility = View.GONE
        binding.sickLeavesSelector.visibility = View.GONE
        binding.daysOffSelector.visibility = View.GONE

        when (cardView) {
            binding.daysOffCardView -> {
                binding.daysOffSelector.visibility = View.VISIBLE
            }

            binding.sickLeavesCardView -> {
                binding.sickLeavesSelector.visibility = View.VISIBLE
            }

            binding.businessTripsCardView -> {
                binding.businessTripsSelector.visibility = View.VISIBLE
            }

            binding.vacationsCardView -> {
                binding.vacationsSelector.visibility = View.VISIBLE
            }
        }
    }
}