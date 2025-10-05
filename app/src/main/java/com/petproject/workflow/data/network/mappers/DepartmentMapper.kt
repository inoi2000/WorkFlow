package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.DepartmentDto
import com.petproject.workflow.data.network.models.EmployeeDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Department
import com.petproject.workflow.domain.entities.Employee
import javax.inject.Inject

@ApplicationScope
class DepartmentMapper @Inject constructor() {

    fun mapDtoToEntity(dto: DepartmentDto): Department {
        return Department(
            id = dto.id,
            name = dto.name,
            staff = dto.staff?.map {
                Employee(
                    id = it.id,
                    name = it.name,
                    phone = it.phone,
                    email = it.email
                )
            }
        )
    }

    fun mapEntityToDto(entity: Department): DepartmentDto {
        return DepartmentDto(
            id = entity.id,
            name = entity.name,
            staff = entity.staff?.map {
                EmployeeDto(
                    id = it.id,
                    name = it.name,
                    phone = it.phone,
                    email = it.email
                )
            }
        )
    }
}