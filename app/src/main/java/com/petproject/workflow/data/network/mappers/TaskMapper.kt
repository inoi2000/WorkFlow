package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.TaskDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import javax.inject.Inject

@ApplicationScope
class TaskMapper @Inject constructor() {

    fun mapDtoToEntity(dto: TaskDto): Task {
        return Task(
            id = dto.id,
            description = dto.description,
            status = TaskStatus.fromString(dto.status),
            priority = TaskPriority.fromString(dto.priority),
            creation = dto.creation,
            deadline = dto.deadline,
            executor = dto.executor?.let { Employee(
                id = it.id,
                name = it.name
            ) },
            inspector = dto.inspector?.let { Employee(
                id = it.id,
                name = it.name
            ) }
        )
    }
}