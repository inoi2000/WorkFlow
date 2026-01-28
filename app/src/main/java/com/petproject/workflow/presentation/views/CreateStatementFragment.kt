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
import com.petproject.workflow.presentation.utils.SelectionEmployeeArg
import com.petproject.workflow.presentation.viewmodels.CreateStatementViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import com.petproject.workflow.presentation.views.adapters.EmployeeInfoViewHolder
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

        binding.btnAddEmployee.setOnClickListener {
            selectEmployee()
        }

    }

    private fun selectEmployee() {
        val action = CreateStatementFragmentDirections
            .actionCreateStatementFragmentToSelectionEmployeeFragment(
                SelectionEmployeeArg(
                    {
                        emptyList()
                    },
                    { employee: Employee ->
                        val driver = EmployeeInfoViewHolder(
                            binding.employeeInfo,
                            viewModel.requestManager)
                        driver.bind(employee) {}

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