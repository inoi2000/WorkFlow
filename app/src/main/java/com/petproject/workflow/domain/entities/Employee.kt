package com.petproject.workflow.domain.entities

data class Employee(
    val id: String,
    val name: String,
    val position: String? = null,
    val department: Department? = null,
    val absences: List<Absence>? = null,
    val tasks: List<Task>? = null,
    val onApproval: List<Task>? = null
)