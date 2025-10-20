package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.PolicyDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.entities.Policy
import javax.inject.Inject

@ApplicationScope
class PolicyMapper @Inject constructor() {

    fun mapDtoToEntity(dto: PolicyDto): Policy {
        return Policy(
            id = dto.id,
            type = AbsenceType.valueOf(dto.type),
            maxDurationDays = dto.maxDurationDays,
            requiresApproval = dto.requiresApproval,
            canApprovePositionIds = dto.canApprovePositionIds
        )
    }

    fun mapEntityToDto(entity: Policy): PolicyDto {
        return PolicyDto(
            id = entity.id,
            type = entity.type.name,
            maxDurationDays = entity.maxDurationDays,
            requiresApproval = entity.requiresApproval,
            canApprovePositionIds = entity.canApprovePositionIds
        )
    }
}