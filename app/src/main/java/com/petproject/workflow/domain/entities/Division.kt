package com.petproject.workflow.domain.entities

data class Division(
    val id: String,
    val name: String,
    val head: Employee,
    val staff: List<Employee>
)