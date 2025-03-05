package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.TaskDto
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskStatus

object TaskMapper {

    private val employeeMapper = EmployeeMapper

    fun mapDtoToEntity(dto: TaskDto): Task {
        return Task(
            id = dto.id,
            description = dto.description,
            status = TaskStatus.fromString(dto.status),
            creation = dto.creation,
            deadline = dto.deadline,
            executor = dto.executor?.let { employeeMapper.mapDtoToEntity(it) },
            inspector = dto.inspector?.let { employeeMapper.mapDtoToEntity(it) }
        )
    }
}