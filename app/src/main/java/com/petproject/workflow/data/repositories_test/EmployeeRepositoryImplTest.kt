package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.data.network.MainApiService
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.mappers.EmployeeMapper
import com.petproject.workflow.domain.entities.BusinessTrip
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.entities.Vacation
import com.petproject.workflow.domain.repositories.EmployeeRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class EmployeeRepositoryImplTest @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val mainApiService: MainApiService
) : EmployeeRepository {

    override suspend fun getEmployee(id: String): Employee {
        val businessTrip = BusinessTrip(
            id = UUID.randomUUID().toString(),
            start = LocalDate.of(2025,3,21),
            end = LocalDate.of(2025,4,12),
            isApproval = true,
            place = "Екатеренбург"
        )
        val vacation = Vacation(
            id = UUID.randomUUID().toString(),
            start = LocalDate.of(2025,5,10),
            end = LocalDate.of(2025,5,15),
            isApproval = true,
        )
        val executionTask1 = Task(
            id = UUID.randomUUID().toString(),
            description = "Распечатать журналы инструкатажа по пожарной безопасности за 2025 год",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,5,10),
            status = TaskStatus.NEW,
            priority = TaskPriority.COMMON,
            executor = null,
            inspector = null
        )
        val executionTask2 = Task(
            id = UUID.randomUUID().toString(),
            description = "Подготовить документацию по проекту сдачи недвижимого имущесто в субаренду в соответствии с текущеми контрактами",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,4,15),
            status = TaskStatus.NEW,
            priority = TaskPriority.COMMON,
            executor = null,
            inspector = null
        )
        val inspectionTask = Task(
            id = UUID.randomUUID().toString(),
            description = "Осуществить закупку кретически важного оборудования на базу в соответствии с текущеми нуждами предприятия",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,4,15),
            status = TaskStatus.ON_APPROVAL,
            priority = TaskPriority.COMMON,
            executor = null,
            inspector = null
        )
        return Employee(
            id = UUID.randomUUID().toString(),
            name = "Иванов Иван Иванович",
            position = "Администратор",
            department = null,
            businessTrips = listOf(businessTrip),
            vacations = listOf(vacation),
            tasks = listOf(executionTask1, executionTask2),
            onApproval = listOf(inspectionTask)
        )
    }
}