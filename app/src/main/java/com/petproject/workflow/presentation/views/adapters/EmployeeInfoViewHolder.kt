package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemEmployeeInfoBinding
import com.petproject.workflow.domain.entities.Employee

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