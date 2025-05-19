package com.petproject.workflow.domain.entities

enum class TaskStatus {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    ON_APPROVAL,
    NOT_APPROVAL;
}