package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.VacationDto
import com.petproject.workflow.domain.entities.Vacation

object VacationMapper {

    fun mapDtoToEntity(dto: VacationDto): Vacation {
        return Vacation(
            id = dto.id,
            start = dto.start,
            end = dto.end
        )
    }
}