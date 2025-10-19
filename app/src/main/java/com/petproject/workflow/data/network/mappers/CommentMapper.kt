package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.CommentDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.CommentStatus
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class CommentMapper @Inject constructor() {

    fun mapDtoToEntity(dto: CommentDto): Comment {
        return Comment(
            id = dto.id,
            text = dto.text,
            createdAt = LocalDateTime.parse(dto.createdAt),
            commentStatus = CommentStatus.valueOf(dto.commentStatus),
            taskId = dto.taskId
        )
    }

    fun mapEntityToDto(entity: Comment): CommentDto {
        return CommentDto(
            id = entity.id,
            text = entity.text,
            commentStatus = entity.commentStatus.name,
            createdAt = entity.createdAt.toString(),
            taskId = entity.taskId
        )
    }
}