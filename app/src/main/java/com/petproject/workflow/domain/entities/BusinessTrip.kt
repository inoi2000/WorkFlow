package com.petproject.workflow.domain.entities

data class BusinessTrip(
    val id: String = "",
    val start: String = "",
    val end: String = "",
    val place: String = "",
    val isApproval: Boolean = false
)
