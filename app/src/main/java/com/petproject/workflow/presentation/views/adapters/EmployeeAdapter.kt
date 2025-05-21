package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemEmployeeInfoBinding
import com.petproject.workflow.domain.entities.Employee

class EmployeeAdapter :
    ListAdapter<Employee, EmployeeAdapter.EmployeeInfoViewHolder>(EmployeeDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeInfoBinding.inflate(inflater, parent, false)
        return EmployeeInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeInfoViewHolder, position: Int) {
        val employee = getItem(position)
        holder.binding.employee = employee
    }

    class EmployeeInfoViewHolder(
        val binding: ItemEmployeeInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}