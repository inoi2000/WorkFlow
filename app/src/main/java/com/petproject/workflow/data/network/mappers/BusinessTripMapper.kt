package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.BusinessTripDto
import com.petproject.workflow.domain.entities.BusinessTrip

object BusinessTripMapper {

    fun mapDtoToEntity(dto: BusinessTripDto): BusinessTrip {
        return BusinessTrip(
            id = dto.id,
            start = dto.start,
            end = dto.end,
            place = dto.place
        )
    }
}