package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.PositionDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Position
import javax.inject.Inject

@ApplicationScope
class PositionMapper @Inject constructor() {

    fun mapDtoToEntity(dto: PositionDto): Position {
        return Position(
            id = dto.id,
            name = dto.name,
            level = dto.level
        )
    }
}