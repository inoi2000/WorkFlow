package com.petproject.workflow.domain.entities

data class Employee(
    val id: String = "",
    val name: String = "",
    val gender: String = GENDER_MAIE,
    val phone: String = "",
    val email: String = "",
    val dob: String = "",
    val registered: String = "",

    val position: String = "",
    val divisionId: String = "",

    val businessTrips: List<BusinessTrip> = mutableListOf(),
    val vacations: List<Vacation> = mutableListOf(),

    val tasks: List<Task> = mutableListOf(),

    val onApproval: List<Approval>? = mutableListOf()
) {
    companion object {
        const val GENDER_MAIE: String = "male"
        const val GENDER_FEMALE: String = "female"
    }
}