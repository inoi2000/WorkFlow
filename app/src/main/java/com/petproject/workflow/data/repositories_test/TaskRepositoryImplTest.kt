package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Comment
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

    private var executionTasksList: List<Task>
    private var inspectingTasksList: List<Task>

    init {
        val employee1 = Employee(
            id = UUID.randomUUID().toString(),
            name = "Иванов Иван Иванович",
            position = "Администратор",
            department = null,
            absences = null,
            tasks = null,
            onApproval = null
        )

        val employee2 = Employee(
            id = UUID.randomUUID().toString(),
            name = "Петров Петр Петрович",
            position = "Водитель",
            department = null,
            absences = null,
            tasks = null,
            onApproval = null
        )

        var executionTask1: Task? = null
        val comment1ToTask1 = Comment(
            id = UUID.randomUUID().toString(),
            text = "Занеси в мой кабинет готовый результат",
            creation = LocalDate.now(),
            task = executionTask1
        )

        val comment2ToTask1 = Comment(
            id = UUID.randomUUID().toString(),
            text = "Распечатай в двух экземплярах",
            creation = LocalDate.now(),
            task = executionTask1
        )
        executionTask1 = Task(
            id = UUID.randomUUID().toString(),
            description = "Распечатать журналы инструкатажа по пожарной безопасности за 2025 год",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 5, 10),
            status = TaskStatus.NEW,
            priority = TaskPriority.COMMON,
            destination = null,
            executor = employee1,
            inspector = employee2,
            comments = listOf(comment1ToTask1, comment2ToTask1)
        )

        val executionTask2 = Task(
            id = UUID.randomUUID().toString(),
            description = "Подготовить документацию по проекту сдачи недвижимого имущесто в субаренду в соответствии с текущеми контрактами",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 4, 15),
            status = TaskStatus.NEW,
            priority = TaskPriority.URGENT,
            destination = "г. Москва, ул. Пречистенка, д.7",
            executor = employee1,
            inspector = employee2
        )
        val inspectionTask = Task(
            id = UUID.randomUUID().toString(),
            description = "Осуществить закупку кретически важного оборудования на базу в соответствии с текущеми нуждами предприятия",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 4, 15),
            status = TaskStatus.ON_APPROVAL,
            priority = TaskPriority.COMMON,
            destination = "г. Оренбург, ул. Сенная 2а",
            executor = employee2,
            inspector = employee1
        )

        executionTasksList = listOf(executionTask1, executionTask2)
        inspectingTasksList = listOf(inspectionTask)
    }


    override suspend fun getAllExecutorTasks(): List<Task> {
        return executionTasksList
    }

    override suspend fun getExecutorTask(id: String): Task {
        return executionTasksList.first { it.id == id }
    }

    override suspend fun getTaskComments(taskId: String): List<Comment> {
        return executionTasksList.first { it.id == taskId }.comments ?: listOf()
    }

    override suspend fun getAllInspectorTasks(): List<Task> {
        return inspectingTasksList
    }

    override suspend fun getInspectorTask(id: String): Task {
        return inspectingTasksList.first { it.id == id }
    }
}