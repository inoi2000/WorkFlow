package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.AnnouncementDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Announcement
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class AnnouncementMapper @Inject constructor() {

    fun mapDtoToEntity(dto: AnnouncementDto): Announcement {
        return Announcement(
            id = dto.id,
            title = dto.title,
            createdAt = LocalDateTime.parse(dto.createdAt),
            content = dto.content,
            imgUrl = dto.imgUrl
        )
    }

    fun mapEntityToDto(entity: Announcement): AnnouncementDto {
        return AnnouncementDto(
            id = entity.id,
            title = entity.title,
            createdAt = entity.createdAt.toString(),
            content = entity.content,
            imgUrl = entity.imgUrl
        )
    }
}