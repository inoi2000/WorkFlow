package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemEmployeeInfoBinding
import com.petproject.workflow.domain.entities.Employee

class EmployeeAdapter(
    private val onEmployeeClick: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeInfoViewHolder>(EmployeeDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeInfoViewHolder =
        EmployeeInfoViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: EmployeeInfoViewHolder, position: Int) =
        holder.bind(
            getItem(position),
            onEmployeeClick
        )
}