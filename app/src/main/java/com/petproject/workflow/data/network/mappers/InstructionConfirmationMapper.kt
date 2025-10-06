package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.InstructionConfirmationDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.InstructionConfirmation
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class InstructionConfirmationMapper @Inject constructor() {

    fun mapDtoToEntity(dto: InstructionConfirmationDto): InstructionConfirmation {
        return InstructionConfirmation(
            employeeId = dto.employeeId,
            isConfirmed = dto.isConfirmed,
            confirmedAt = LocalDateTime.parse(dto.confirmedAt)
        )
    }

    fun mapEntityToDto(entity: InstructionConfirmation): InstructionConfirmationDto {
        return InstructionConfirmationDto(
            employeeId = entity.employeeId,
            isConfirmed = entity.isConfirmed,
            confirmedAt = entity.confirmedAt.toString()
        )
    }
}