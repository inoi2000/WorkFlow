package com.petproject.workflow.presentation.utils

import android.os.Parcelable
import com.petproject.workflow.domain.entities.Employee
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionEmployeeArg(
    val getEmployees: suspend () -> List<Employee>,
    val onEmployeeSelected: (Employee) -> Unit
) : Parcelable