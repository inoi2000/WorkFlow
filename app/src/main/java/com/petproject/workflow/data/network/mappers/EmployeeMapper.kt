package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.EmployeeDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Employee
import javax.inject.Inject

@ApplicationScope
class EmployeeMapper @Inject constructor(
    private val positionMapper: PositionMapper,
    private val departmentMapper: DepartmentMapper,
    private val absenceMapper: AbsenceMapper
)  {

    fun mapDtoToEntity(dto: EmployeeDto): Employee {
        return Employee(
            id = dto.id,
            name = dto.name,
            position = dto.position?.let { positionMapper.mapDtoToEntity(it) },
            department = dto.department?.let { departmentMapper.mapDtoToEntity(it) },
            absences = dto.absence?.map { absenceMapper.mapDtoToEntity(it) },
        )
    }

    fun mapEntityToDto(entity: Employee): EmployeeDto {
        return EmployeeDto(
            id = entity.id,
            name = entity.name,
            position = entity.position?.let { positionMapper.mapEntityToDto(it) },
            absence = null,
            department = null
        )
    }
}