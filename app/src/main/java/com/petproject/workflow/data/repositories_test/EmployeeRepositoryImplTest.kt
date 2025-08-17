package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.data.network.EmployeeApiService
import com.petproject.workflow.data.network.mappers.EmployeeMapper
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceStatus
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.entities.Department
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Position
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.repositories.EmployeeRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class EmployeeRepositoryImplTest @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val employeeApiService: EmployeeApiService
) : EmployeeRepository {

    override suspend fun getEmployee(id: String): Employee {
        val businessTrip = Absence(
            id = "00112233-4455-6677-8899-aabbccddeeff",
            type = AbsenceType.BUSINESS_TRIP,
            status = AbsenceStatus.APPROVED,
            start = LocalDate.of(2025,3,21),
            end = LocalDate.of(2025,4,12),
            isApproval = true,
            place = "Екатеренбург"
        )
        val vacation = Absence(
            id = "550e8400-e29b-41d4-a716-446655440000",
            type = AbsenceType.VACATION,
            status = AbsenceStatus.APPROVED,
            start = LocalDate.of(2025,5,10),
            end = LocalDate.of(2025,5,15),
            isApproval = true,
        )
        val executionTask1 = Task(
            id = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
            description = "Распечатать журналы инструкатажа по пожарной безопасности за 2025 год",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,5,10),
            status = TaskStatus.NEW,
            priority = TaskPriority.COMMON,
            executor = null,
            inspector = null,
            shouldBeInspected = true
        )
        val executionTask2 = Task(
            id = "e58ed763-928c-4155-bee9-fdbaaadc15f3",
            description = "Подготовить документацию по проекту сдачи недвижимого имущесто в субаренду в соответствии с текущеми контрактами",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,4,15),
            status = TaskStatus.NEW,
            priority = TaskPriority.COMMON,
            executor = null,
            inspector = null,
            shouldBeInspected = true
        )
        val inspectionTask = Task(
            id = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
            description = "Осуществить закупку кретически важного оборудования на базу в соответствии с текущеми нуждами предприятия",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,4,15),
            status = TaskStatus.ON_APPROVAL,
            priority = TaskPriority.COMMON,
            executor = null,
            inspector = null,
            shouldBeInspected = true
        )
        return Employee(
            id = UUID.randomUUID().toString(),
            name = "Иванов Иван Иванович",
            position = Position(UUID.randomUUID().toString(), "Администратор", 700),
            department = null,
            absences = listOf(vacation, businessTrip)
        )
    }

    override suspend fun getCurrentEmployee(): Employee {
        return Employee(
            id = UUID.randomUUID().toString(),
            name = "Иванов Иван Иванович",
            position = Position(UUID.randomUUID().toString(), "Администратор", 700),
            department = Department(
                id = UUID.randomUUID().toString(),
                name = "Менеджмент"
            ),
            absences = null
        )
    }

    override suspend fun getAllEmployeesForAssignTask(): List<Employee> {
        val list = listOf(
            Employee(
                id = UUID.randomUUID().toString(),
                name = "Иванов Иван Иванович",
                position = Position(UUID.randomUUID().toString(), "Администратор", 700),
                department = Department(
                    id = UUID.randomUUID().toString(),
                    name = "Менеджмент"
                ),
                absences = null
            ),
            Employee(
                id = UUID.randomUUID().toString(),
                name = "Петров Иван Иванович",
                position = Position(UUID.randomUUID().toString(), "Администратор", 700),
                department = Department(
                    id = UUID.randomUUID().toString(),
                    name = "Менеджмент"
                ),
                absences = null
            ),
            Employee(
                id = UUID.randomUUID().toString(),
                name = "Сидоров Иван Иванович",
                position = Position(UUID.randomUUID().toString(), "Администратор", 700),
                department = Department(
                    id = UUID.randomUUID().toString(),
                    name = "Менеджмент"
                ),
                absences = null
            )
        )
        return list
    }
}