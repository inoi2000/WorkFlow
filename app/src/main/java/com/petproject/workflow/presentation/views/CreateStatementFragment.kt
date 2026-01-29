package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateStatementBinding
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.presentation.utils.SelectionCarArg
import com.petproject.workflow.presentation.utils.SelectionEmployeeArg
import com.petproject.workflow.presentation.utils.SelectionTrailerArg
import com.petproject.workflow.presentation.viewmodels.CreateStatementViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.CarInfoViewHolder
import com.petproject.workflow.presentation.views.adapters.EmployeeInfoViewHolder
import com.petproject.workflow.presentation.views.adapters.TrailerInfoViewHolder
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

        binding.btnAddCar.setOnClickListener {
            selectCar()
        }

        binding.btnAddTrailer.setOnClickListener {
            selectTrailer()
        }

        binding.btnAddEmployee.setOnClickListener {
            selectEmployee()
        }

        viewModel.car.observe(viewLifecycleOwner) { car ->
            car?.let {
                val carVH = CarInfoViewHolder(
                    binding.carInfo,
                ) { }
                carVH.bind(it)

                binding.btnAddCar.visibility = View.GONE
                binding.btnRemoveCar.visibility = View.VISIBLE
                binding.carInfo.root.visibility = View.VISIBLE
            }
        }

        viewModel.trailer.observe(viewLifecycleOwner) { trailer ->
            trailer?.let {
                val trailerVH = TrailerInfoViewHolder(
                    binding.trailerInfo
                ) { }
                trailerVH.bind(it)

                binding.btnAddTrailer.visibility = View.GONE
                binding.btnRemoveTrailer.visibility = View.VISIBLE
                binding.trailerInfo.root.visibility = View.VISIBLE
            }
        }

        viewModel.driver.observe(viewLifecycleOwner) { employee ->
            employee?.let {
                val driverVH = EmployeeInfoViewHolder(
                    binding.employeeInfo,
                    viewModel.requestManager)
                driverVH.bind(it) { }

                binding.btnAddEmployee.visibility = View.GONE
                binding.btnRemoveEmployee.visibility = View.VISIBLE
                binding.employeeInfo.root.visibility = View.VISIBLE
            }
        }
    }

    private fun selectCar() {
        val action = CreateStatementFragmentDirections
            .actionCreateStatementFragmentToSelectionCarFragment(
                SelectionCarArg { car ->
                    viewModel.setCar(car)
                    findNavController().navigateUp()
                }
            )
        findNavController().navigate(action)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}