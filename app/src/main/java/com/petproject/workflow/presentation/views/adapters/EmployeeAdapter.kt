package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemEmployeeInfoBinding
import com.petproject.workflow.domain.entities.Employee

class EmployeeAdapter(
    private val onEmployeeClick: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeAdapter.EmployeeInfoViewHolder>(EmployeeDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeInfoViewHolder =
        EmployeeInfoViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: EmployeeInfoViewHolder, position: Int) =
        holder.bind(
            getItem(position),
            onEmployeeClick
        )

    class EmployeeInfoViewHolder(
        val binding: ItemEmployeeInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun inflateFrom(viewGroup: ViewGroup): EmployeeInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemEmployeeInfoBinding.inflate(inflater, viewGroup, false)
                return EmployeeInfoViewHolder(binding)
            }
        }

        fun bind(
            employee: Employee,
            onEmployeeClick: (Employee) -> Unit
        ) {
            binding.employee = employee
            binding.root.setOnClickListener {
                onEmployeeClick(employee)
            }
        }
    }
}