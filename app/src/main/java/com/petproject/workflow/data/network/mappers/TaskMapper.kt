package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.TaskDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import java.time.LocalDate
import javax.inject.Inject

@ApplicationScope
class TaskMapper @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val commentMapper: CommentMapper
) {

    fun mapDtoToEntity(dto: TaskDto): Task {
        return Task(
            id = dto.id,
            description = dto.description,
            status = TaskStatus.valueOf(dto.status),
            priority = TaskPriority.valueOf(dto.priority),
            creation = LocalDate.parse(dto.creation),
            deadline = LocalDate.parse(dto.deadline),
            destination = dto.destination,
            executor = dto.executor?.let { employeeMapper.mapDtoToEntity(it) },
            inspector = dto.inspector?.let { employeeMapper.mapDtoToEntity(it) },
            shouldBeInspected = dto.shouldBeInspected,
            comments = dto.comments.map { commentMapper.mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Task): TaskDto {
        return TaskDto(
            id = entity.id,
            description = entity.description,
            status = entity.status.name,
            priority = entity.priority.name,
            creation = entity.creation.toString(),
            deadline = entity.deadline.toString(),
            destination = entity.destination,
            executor = entity.executor?.let { employeeMapper.mapEntityToDto(it) },
            inspector = entity.inspector?.let { employeeMapper.mapEntityToDto(it) },
            shouldBeInspected = entity.shouldBeInspected,
            comments = entity.comments?.map { commentMapper.mapEntityToDto(it) } ?: listOf()
        )
    }
}