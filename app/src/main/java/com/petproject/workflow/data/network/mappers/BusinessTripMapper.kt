package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.BusinessTripDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.BusinessTrip
import javax.inject.Inject

@ApplicationScope
class BusinessTripMapper @Inject constructor() {

    fun mapDtoToEntity(dto: BusinessTripDto): BusinessTrip {
        return BusinessTrip(
            id = dto.id,
            start = dto.start,
            end = dto.end,
            place = dto.place
        )
    }
}