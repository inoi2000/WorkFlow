package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.MainApiService
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.mappers.EmployeeMapper
import com.petproject.workflow.domain.entities.Department
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.repositories.EmployeeRepository
import java.util.UUID
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val mainApiService: MainApiService
) : EmployeeRepository {

    override suspend fun getEmployee(id: String): Employee {
        //TODO добавить обновления пользователя через websocket
        val response = mainApiService.getEmployee(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return employeeMapper.mapDtoToEntity(it)
            }
        }
        throw AuthException()
    }

    override suspend fun getCurrentEmployee(): Employee {
//        TODO("Not yet implemented")
        return Employee(
            id = UUID.randomUUID().toString(),
            name = "Иванов Иван Иванович",
            position = "Администратор",
            department = Department(
                id = UUID.randomUUID().toString(),
                name = "Менеджмент"
            ),
            absences = null,
            tasks = null,
            onApproval = null,
            canAssignTask = false
        )
    }

    override suspend fun getAllEmployeesForAssignTask(): List<Employee> {
//        TODO("Not yet implemented")
        val list = listOf(
            Employee(
                id = UUID.randomUUID().toString(),
                name = "Иванов Иван Иванович",
                position = "Администратор",
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
                position = "Администратор",
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
                position = "Администратор",
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