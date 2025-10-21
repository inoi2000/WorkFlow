package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.ApprovalDto
import com.petproject.workflow.domain.entities.Approval
import javax.inject.Inject

class ApprovalMapper @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val absenceMapper: AbsenceMapper
) {
    fun mapDtoToEntity(dto: ApprovalDto): Approval {
        return Approval(
            id = dto.id,
            absence = absenceMapper.mapDtoToEntity(dto.absence),
            approver = employeeMapper.mapDtoToEntity(dto.approver),
            description = dto.description
        )
    }

    fun mapEntityToDto(entity: Approval): ApprovalDto {
        return ApprovalDto(
            id = entity.id,
            absence = absenceMapper.mapEntityToDto(entity.absence),
            approver = employeeMapper.mapEntityToDto(entity.approver),
            description = entity.description
        )
    }
}