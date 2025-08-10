package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee (
    val id: String,
    val name: String,
    val position: Position? = null,

    val department: Department? = null,

    val absences: List<Absence>? = null,
    val tasks: List<Task>? = null,
    val onApproval: List<Task>? = null,

    val canAssignTask: Boolean
) : Parcelable