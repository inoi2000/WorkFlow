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
            status = dto.status,
            instructorId = dto.instructorId,
            data = dto.data,
            validUntil = dto.validUntil,
            instructionConfirmation = dto.instructionConfirmation,
            instructionConfirmations = dto.instructionConfirmations
        )
    }

    fun mapEntityToDto(entity: Instruction): InstructionDto {
        return InstructionDto(
            id = entity.id,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            status = entity.status,
            instructorId = entity.instructorId,
            data = entity.data,
            validUntil = entity.validUntil,
            instructionConfirmation = entity.instructionConfirmation,
            instructionConfirmations = entity.instructionConfirmations
        )
    }
}