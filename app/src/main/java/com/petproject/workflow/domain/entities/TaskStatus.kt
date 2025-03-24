package com.petproject.workflow.domain.entities

enum class TaskStatus {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    ON_APPROVAL,
    NOT_APPROVAL;

    companion object {
        fun fromString(status: String): TaskStatus {
            return when(status) {
                NEW.toString() -> NEW
                IN_PROGRESS.toString() -> IN_PROGRESS
                COMPLETED.toString() -> COMPLETED
                FAILED.toString() -> FAILED
                ON_APPROVAL.toString() -> ON_APPROVAL
                NOT_APPROVAL.toString() -> NOT_APPROVAL

                else -> { throw ClassCastException() }
            }
        }
    }
}