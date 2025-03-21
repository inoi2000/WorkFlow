package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.repositories.TaskRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@ApplicationScope //Only for test in real implementation without scope
class TaskRepositoryImplTest @Inject constructor(
    private val tokenManager: TokenManager
) : TaskRepository {

    private var employee1: Employee
    private var employee2: Employee
    private var executionTask1: Task
    private var executionTask2: Task
    private var inspectionTask: Task

    private var executionTasksList: List<Task>
    private var inspectingTasksList: List<Task>

    init {
        employee1 = Employee(
            id = UUID.randomUUID().toString(),
            name = "Иванов Иван Иванович",
            position = "Администратор",
            department = null,
            businessTrips = null,
            vacations = null,
            tasks = null,
            onApproval = null
        )

        employee2 = Employee(
            id = UUID.randomUUID().toString(),
            name = "Петров Петр Петрович",
            position = "Водитель",
            department = null,
            businessTrips = null,
            vacations = null,
            tasks = null,
            onApproval = null
        )

        executionTask1 = Task(
            id = UUID.randomUUID().toString(),
            description = "Распечатать журналы инструкатажа по пожарной безопасности за 2025 год",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 5, 10),
            status = TaskStatus.NEW,
            priority = TaskPriority.COMMON,
            executor = employee1,
            inspector = employee2
        )

        executionTask2 = Task(
            id = UUID.randomUUID().toString(),
            description = "Подготовить документацию по проекту сдачи недвижимого имущесто в субаренду в соответствии с текущеми контрактами",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 4, 15),
            status = TaskStatus.NEW,
            priority = TaskPriority.URGENT,
            executor = employee1,
            inspector = employee2
        )
        inspectionTask = Task(
            id = UUID.randomUUID().toString(),
            description = "Осуществить закупку кретически важного оборудования на базу в соответствии с текущеми нуждами предприятия",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 4, 15),
            status = TaskStatus.ON_APPROVAL,
            priority = TaskPriority.COMMON,
            executor = employee2,
            inspector = employee1
        )

        executionTasksList = listOf(executionTask1, executionTask2)
        inspectingTasksList = listOf(inspectionTask)
    }


    override suspend fun getAllExecutingTasks(): List<Task> {
        return executionTasksList
    }

    override suspend fun getExecutingTask(id: String): Task {
        return executionTasksList.first { it.id == id }
    }
}