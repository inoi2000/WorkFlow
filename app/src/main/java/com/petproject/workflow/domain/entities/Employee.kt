package com.petproject.workflow.domain.entities

data class Employee(
    val id: String,
    val name: String,
    val gender: String,
    val phone: String,
    val email: String,
    val dob: String,
    val registered: String,
    val pictureUri: EmployeePictureUri,

    val position: String,
    val division: Division,

    val businessTrips: List<BusinessTrip>,
    val vacations: List<Vacation>,

    val tasks: List<Task>
) {
    companion object {
        const val GENDER_MAIE: String = "male"
        const val GENDER_FEMALE: String = "female"
    }
}