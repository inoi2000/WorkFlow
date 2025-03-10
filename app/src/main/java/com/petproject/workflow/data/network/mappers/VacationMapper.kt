package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.VacationDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Vacation
import javax.inject.Inject

@ApplicationScope
class VacationMapper @Inject constructor() {

    fun mapDtoToEntity(dto: VacationDto): Vacation {
        return Vacation(
            id = dto.id,
            start = dto.start,
            end = dto.end
        )
    }
}