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
//        TODO("Not yet implemented")
        val list = listOf(
            Employee(
                id = UUID.randomUUID().toString(),
                name = "Иванов Иван Иванович",
                position = Position(UUID.randomUUID().toString(), "Администратор", 700),
                department = Department(
                    id = UUID.randomUUID().toString(),
                    name = "Менеджмент"
                ),
                absences = null,
                tasks = null,
                onApproval = null,
                canAssignTask = false
            ),
            Employee(
                id = UUID.randomUUID().toString(),
                name = "Петров Иван Иванович",
                position = Position(UUID.randomUUID().toString(), "Администратор", 700),
                department = Department(
                    id = UUID.randomUUID().toString(),
                    name = "Менеджмент"
                ),
                absences = null,
                tasks = null,
                onApproval = null,
                canAssignTask = false
            ),
            Employee(
                id = UUID.randomUUID().toString(),
                name = "Сидоров Иван Иванович",
                position = Position(UUID.randomUUID().toString(), "Администратор", 700),
                department = Department(
                    id = UUID.randomUUID().toString(),
                    name = "Менеджмент"
                ),
                absences = null,
                tasks = null,
                onApproval = null,
                canAssignTask = false
            )
        )
        return list
    }
}