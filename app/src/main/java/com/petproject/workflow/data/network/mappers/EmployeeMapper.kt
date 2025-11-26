package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.ApiConfig
import com.petproject.workflow.data.network.models.EmployeeDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Employee
import javax.inject.Inject

@ApplicationScope
class EmployeeMapper @Inject constructor(
    private val positionMapper: PositionMapper,
    private val departmentMapper: DepartmentMapper,
)  {

    fun mapDtoToEntity(dto: EmployeeDto): Employee {
        return Employee(
            id = dto.id,
            name = dto.name,
            photoUrl = fromKeyToUrl(dto.id),
            phone = dto.phone,
            email = dto.email,
            position = dto.position?.let { positionMapper.mapDtoToEntity(it) },
            department = dto.department?.let { departmentMapper.mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Employee): EmployeeDto {
        return EmployeeDto(
            id = entity.id,
            name = entity.name,
            phone = entity.phone,
            email = entity.email,
            position = entity.position?.let { positionMapper.mapEntityToDto(it) },
            department = entity.department?.let { departmentMapper.mapEntityToDto(it) }
        )
    }

    private fun fromKeyToUrl(photoKey: String?): String? {
        return photoKey?.let {
            String.format(ApiConfig.EMPLOYEE_PHOTO_URI_PATTERN, it)
        }
    }
}