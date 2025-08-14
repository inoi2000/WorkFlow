package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.EmployeeApiService
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.mappers.EmployeeMapper
import com.petproject.workflow.data.network.utils.TokensManager
import com.petproject.workflow.domain.entities.Department
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Position
import com.petproject.workflow.domain.repositories.EmployeeRepository
import java.util.UUID
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val employeeApiService: EmployeeApiService,
    private val tokensManager: TokensManager
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
        val accessToken = tokensManager.getAccessToken()
        accessToken?.let {
            val employeeId = TokensManager.getIdFromAccessToken(it)
            employeeId?.let {
                return getEmployee(employeeId)
            }
        }
        throw AuthException()
    }

    override suspend fun getAllEmployeesForAssignTask(): List<Employee> {
        val accessToken = tokensManager.getAccessToken()
        accessToken?.let { token ->
            val employeeId = TokensManager.getIdFromAccessToken(token)
            employeeId?.let { id ->
                val employees = employeeApiService.getSubordinateEmployees(id)
                return employees
                    .map { dto -> employeeMapper.mapDtoToEntity(dto) }
                    .toList()
            }
        }
        throw AuthException()
    }
}