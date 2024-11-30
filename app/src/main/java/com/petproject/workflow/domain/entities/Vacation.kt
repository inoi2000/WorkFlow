package com.petproject.workflow.domain.entities

data class Vacation(
    val id: String = "",
    val start: String = "",
    val end: String = "",
    val isApproval: Boolean = false
)
