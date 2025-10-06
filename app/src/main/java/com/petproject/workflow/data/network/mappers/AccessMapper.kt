package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.AccessDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Access
import com.petproject.workflow.domain.entities.AccessDurationType
import com.petproject.workflow.domain.entities.DocumentStatus
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class AccessMapper @Inject constructor() {

    fun mapDtoToEntity(dto: AccessDto): Access {
        return Access(
            id = dto.id,
            createdAt = LocalDateTime.parse(dto.createdAt),
            updatedAt = LocalDateTime.parse(dto.updatedAt),
            type = AccessDurationType.valueOf(dto.type),
            status = DocumentStatus.valueOf(dto.status),
            issuerId = dto.issuerId,
            holderId = dto.holderId,
            data = dto.data,
            validUntil = dto.validUntil?.let { LocalDate.parse(it) }
        )
    }

    fun mapEntityToDto(entity: Access): AccessDto {
        return AccessDto(
            id = entity.id,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
            type = entity.type.name,
            status = entity.status.name,
            issuerId = entity.issuerId,
            holderId = entity.holderId,
            data = entity.data,
            validUntil = entity.validUntil?.toString()
        )
    }
}