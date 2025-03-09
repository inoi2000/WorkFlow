package com.petproject.workflow.domain.entities

data class Employee(
    val id: String,
    val name: String,
    val position: String? = null,
    val department: Department? = null,
    val businessTrips: List<BusinessTrip>? = null,
    val vacations: List<Vacation>? = null,
    val tasks: List<Task>? = null,
    val onApproval: List<Task>? = null
)