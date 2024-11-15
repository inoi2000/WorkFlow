package com.petproject.workflow.domain.entities

data class Task(
    val id: String,
    val description: String,
    val creation: String,
    val deadline: String,
    val status: Status
)
