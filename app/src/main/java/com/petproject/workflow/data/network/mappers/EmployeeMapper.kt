package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.EmployeeDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Employee
import javax.inject.Inject

@ApplicationScope
class EmployeeMapper @Inject constructor(
    private val departmentMapper: DepartmentMapper,
    private val taskMapper: TaskMapper,
    private val businessTripMapper: BusinessTripMapper,
    private val vacationMapper: VacationMapper
)  {

    fun mapDtoToEntity(dto: EmployeeDto): Employee {
        return Employee(
            id = dto.id,
            name = dto.name,
            position = dto.position,
            department = dto.department?.let { departmentMapper.mapDtoToEntity(it) },
            businessTrips = dto.businessTrips?.map { businessTripMapper.mapDtoToEntity(it) },
            vacations = dto.vacations?.map { vacationMapper.mapDtoToEntity(it) },
            tasks = dto.executionTasks?.map { taskMapper.mapDtoToEntity(it) },
            onApproval = dto.inspectionTasks?.map { taskMapper.mapDtoToEntity(it) }
        )
    }
}