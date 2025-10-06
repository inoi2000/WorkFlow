package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.InstructionDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.DocumentStatus
import com.petproject.workflow.domain.entities.Instruction
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class InstructionMapper @Inject constructor(
    val instructionConfirmationMapper: InstructionConfirmationMapper
) {

    fun mapDtoToEntity(dto: InstructionDto): Instruction {
        return Instruction(
            id = dto.id,
            createdAt = LocalDateTime.parse(dto.createdAt),
            updatedAt = LocalDateTime.parse(dto.updatedAt),
            status = DocumentStatus.valueOf(dto.status),
            instructorId = dto.instructorId,
            data = dto.data,
            validUntil = LocalDate.parse(dto.validUntil),
            instructionConfirmation = dto.instructionConfirmation?.let {
                instructionConfirmationMapper.mapDtoToEntity(it)
            },
            instructionConfirmations = dto.instructionConfirmations?.map {
                instructionConfirmationMapper.mapDtoToEntity(it)
            }
        )
    }

    fun mapEntityToDto(entity: Instruction): InstructionDto {
        return InstructionDto(
            id = entity.id,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
            status = entity.status.name,
            instructorId = entity.instructorId,
            data = entity.data,
            validUntil = entity.validUntil.toString(),
            instructionConfirmation = entity.instructionConfirmation?.let {
                instructionConfirmationMapper.mapEntityToDto(it)
            },
            instructionConfirmations = entity.instructionConfirmations?.map {
                instructionConfirmationMapper.mapEntityToDto(it)
            }
        )
    }
}