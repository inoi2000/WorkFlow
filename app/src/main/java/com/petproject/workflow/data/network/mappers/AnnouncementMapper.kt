package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.AnnouncementDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Announcement
import java.time.LocalDate
import javax.inject.Inject

@ApplicationScope
class AnnouncementMapper @Inject constructor() {

    fun mapDtoToEntity(dto: AnnouncementDto): Announcement {
        return Announcement(
            id = dto.id,
            title = dto.title,
            postData = LocalDate.parse(dto.postData),
            content = dto.content,
            imgUrl = dto.imgUrl
        )
    }

    fun mapEntityToDto(entity: Announcement): AnnouncementDto {
        return AnnouncementDto(
            id = entity.id,
            title = entity.title,
            postData = entity.postData.toString(),
            content = entity.content,
            imgUrl = entity.imgUrl
        )
    }
}