package com.petproject.workflow.domain.entities

enum class TaskPriority {
    COMMON,
    URGENT;

    companion object {
        fun fromString(priority: String): TaskPriority {
            return when(priority) {
                COMMON.toString() -> COMMON
                URGENT.toString() -> URGENT
                else -> { throw ClassCastException() }
            }
        }
    }
}