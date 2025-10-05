package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.AccessDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Access
import javax.inject.Inject

@ApplicationScope
class AccessMapper @Inject constructor() {

    fun mapDtoToEntity(dto: AccessDto): Access {
        return Access(
            id = dto.id,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt,
            type = dto.type,
            status = dto.status,
            issuerId = dto.issuerId,
            holderId = dto.holderId,
            data = dto.data,
            validUntil = dto.validUntil
        )
    }

    fun mapEntityToDto(entity: Access): AccessDto {
        return AccessDto(
            id = entity.id,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            type = entity.type,
            status = entity.status,
            issuerId = entity.issuerId,
            holderId = entity.holderId,
            data = entity.data,
            validUntil = entity.validUntil
        )
    }
}