package com.petproject.workflow.domain.entities

data class Employee(
    val id: String,
    val name: String,
    val position: String?,
    val department: Department?,
    val businessTrips: List<BusinessTrip>?,
    val vacations: List<Vacation>?,
    val tasks: List<Task>?,
    val onApproval: List<Task>?
)