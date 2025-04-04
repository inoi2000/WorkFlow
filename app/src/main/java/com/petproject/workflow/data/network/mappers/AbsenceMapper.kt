package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.AbsenceDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceStatus
import com.petproject.workflow.domain.entities.AbsenceType
import javax.inject.Inject

@ApplicationScope
class AbsenceMapper @Inject constructor() {

    fun mapDtoToEntity(dto: AbsenceDto): Absence {
        return Absence(
            id = dto.id,
            type = AbsenceType.valueOf(dto.type),
            status = AbsenceStatus.valueOf(dto.status),
            start = dto.start,
            end = dto.end,
            place = dto.place
        )
    }
}