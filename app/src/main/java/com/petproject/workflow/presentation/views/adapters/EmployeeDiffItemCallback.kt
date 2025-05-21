package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Employee

object EmployeeDiffItemCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean =
        oldItem == newItem
}