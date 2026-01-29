package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.EmployeeApiService
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.mappers.EmployeeMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.repositories.EmployeeRepository
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val employeeMapper: EmployeeMapper,
    private val employeeApiService: EmployeeApiService,
) : EmployeeRepository {

    override suspend fun getEmployee(id: String): Employee {
        val response = employeeApiService.getEmployee(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return employeeMapper.mapDtoToEntity(it)
            }
        }
        throw AuthException()
    }

    override suspend fun getCurrentEmployee(): Employee {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        return getEmployee(employeeId)
    }

    override suspend fun getAllEmployeesByQuery(query: String): List<Employee> {
        val employees = employeeApiService.getAllEmployeesByQuery(query)
        return employees.map { dto -> employeeMapper.mapDtoToEntity(dto) }
    }

    override suspend fun getAllEmployeesForAssignTask(): List<Employee> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val employees = employeeApiService.getSubordinateEmployees(employeeId)
        return employees.map { dto -> employeeMapper.mapDtoToEntity(dto) }
    }

    override suspend fun getDriverEmployees(): List<Employee> {
        val employees = employeeApiService.getDriversEmployees()
        return employees.map { dto -> employeeMapper.mapDtoToEntity(dto) }
    }
}