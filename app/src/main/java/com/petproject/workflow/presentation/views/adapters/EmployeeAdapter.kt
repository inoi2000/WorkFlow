package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Employee

class EmployeeAdapter(
    private val requestManager: RequestManager,
    private val onEmployeeClick: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeInfoViewHolder>(EmployeeDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeInfoViewHolder =
        EmployeeInfoViewHolder.inflateFrom(parent, requestManager)

    override fun onBindViewHolder(holder: EmployeeInfoViewHolder, position: Int) =
        holder.bind(
            getItem(position),
            onEmployeeClick
        )
}