package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.DepartmentDto
import com.petproject.workflow.domain.entities.Department

object DepartmentMapper {

    private val employeeMapper = EmployeeMapper

    fun mapDtoToEntity(dto: DepartmentDto): Department {
        return Department(
            id = dto.id,
            name = dto.name,
            staff = dto.staff?.map { employeeMapper.mapDtoToEntity(it) }
        )
    }
}