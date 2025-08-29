package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.CommentDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.CommentStatus
import java.time.LocalDate
import javax.inject.Inject

@ApplicationScope
class CommentMapper @Inject constructor() {

    fun mapDtoToEntity(dto: CommentDto): Comment {
        return Comment(
            id = dto.id,
            text = dto.text,
            creation = LocalDate.parse(dto.creation),
            commentStatus = CommentStatus.valueOf(dto.commentStatus),
            taskId = dto.taskId
        )
    }

    fun mapEntityToDto(entity: Comment): CommentDto {
        return CommentDto(
            id = entity.id,
            text = entity.text,
            commentStatus = entity.commentStatus.name,
            creation = entity.creation.toString(),
            taskId = entity.taskId
        )
    }
}