package com.petproject.workflow.data.repositories

import com.bumptech.glide.RequestManager
import com.petproject.workflow.data.network.ApiConfig
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

    override suspend fun getAllEmployeesForAssignTask(): List<Employee> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val employees = employeeApiService.getSubordinateEmployees(employeeId)
        return employees
            .map { dto -> employeeMapper.mapDtoToEntity(dto) }
    }

    override suspend fun getDriverEmployees(): List<Employee> {
        TODO("Not yet implemented")
    }

    override suspend fun loadEmployeePhoto(employeeId: String, callback: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun loadCurrentEmployeePhoto(callback: (String) -> Unit) {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val uri = String.format(ApiConfig.EMPLOYEE_PHOTO_URI_PATTERN, employeeId)
        callback(uri)
    }
}