package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.InstructionDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Instruction
import javax.inject.Inject

@ApplicationScope
class InstructionMapper @Inject constructor() {

    fun mapDtoToEntity(dto: InstructionDto): Instruction {
        return Instruction(
            id = dto.id,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt,
            data = dto.data,
            status = dto.status,
            isConfirmed = dto.isConfirmed,
            instructorId = dto.instructorId,
            employeeId = dto.employeeId,
            confirmedAt = dto.confirmedAt,
            validUntil = dto.validUntil
        )
    }

    fun mapEntityToDto(entity: Instruction): InstructionDto {
        return InstructionDto(
            id = entity.id,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            data = entity.data,
            status = entity.status,
            isConfirmed = entity.isConfirmed,
            instructorId = entity.instructorId,
            employeeId = entity.employeeId,
            confirmedAt = entity.confirmedAt,
            validUntil = entity.validUntil
        )
    }
}