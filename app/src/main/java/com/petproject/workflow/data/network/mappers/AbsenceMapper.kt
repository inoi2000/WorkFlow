package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.AbsenceDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceStatus
import com.petproject.workflow.domain.entities.AbsenceType
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class AbsenceMapper @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val policyMapper: PolicyMapper
) {

    fun mapDtoToEntity(dto: AbsenceDto): Absence {
        return Absence(
            id = dto.id,
            status = AbsenceStatus.valueOf(dto.status),
            startDate = LocalDate.parse(dto.startDate),
            endDate = LocalDate.parse(dto.endDate),
            place = dto.place,
            employee = employeeMapper.mapDtoToEntity(dto.employee),
            createdBy = employeeMapper.mapDtoToEntity(dto.createdBy),
            policy = policyMapper.mapDtoToEntity(dto.policy),
            createdAt = LocalDateTime.parse(dto.createdAt),
            updatedAt = LocalDateTime.parse(dto.updatedAt)
        )
    }

    fun mapEntityToDto(entity: Absence): AbsenceDto {
        return AbsenceDto(
            id = entity.id,
            status = entity.status.name,
            startDate = entity.startDate.toString(),
            endDate = entity.endDate.toString(),
            place = entity.place,
            employee = employeeMapper.mapEntityToDto(entity.employee),
            createdBy = employeeMapper.mapEntityToDto(entity.createdBy),
            policy = policyMapper.mapEntityToDto(entity.policy),
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString()
        )
    }
}