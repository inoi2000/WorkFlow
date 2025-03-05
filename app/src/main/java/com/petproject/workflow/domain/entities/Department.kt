package com.petproject.workflow.domain.entities

data class Department(
    val id: String,
    val name: String,
    val staff: List<Employee>?
)