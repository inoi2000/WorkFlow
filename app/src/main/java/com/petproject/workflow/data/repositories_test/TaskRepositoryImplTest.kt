package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.data.network.utils.TokensManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.CommentStatus
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Position
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.repositories.TaskRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@ApplicationScope //Only for test in real implementation without scope
class TaskRepositoryImplTest @Inject constructor(
    private val tokensManager: TokensManager
) : TaskRepository {

    private var executionTasksList: MutableList<Task>
    private var inspectingTasksList: MutableList<Task>

    init {
        val employee1 = Employee(
            id = UUID.randomUUID().toString(),
            name = "Иванов Иван Иванович",
            position = Position(UUID.randomUUID().toString(), "Администратор", 700),
            department = null,
            absences = null,
        )

        val employee2 = Employee(
            id = UUID.randomUUID().toString(),
            name = "Петров Петр Петрович",
            position = Position(UUID.randomUUID().toString(), "Администратор", 700),
            department = null,
            absences = null,
        )

        var executionTask1: Task? = null
        executionTask1 = Task(
            id = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
            description = "Распечатать журналы инструкатажа по пожарной безопасности за 2025 год",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 5, 10),
            status = TaskStatus.NEW,
            priority = TaskPriority.COMMON,
            destination = null,
            executor = employee1,
            inspector = employee2,
            comments = listOf(),
            shouldBeInspected = true
        )

        val executionTask2 = Task(
            id = "e58ed763-928c-4155-bee9-fdbaaadc15f3",
            description = "Подготовить документацию по проекту сдачи недвижимого имущесто в субаренду в соответствии с текущеми контрактами",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 4, 15),
            status = TaskStatus.NEW,
            priority = TaskPriority.URGENT,
            destination = "г. Москва, ул. Пречистенка, д.7",
            executor = employee1,
            inspector = employee2,
            shouldBeInspected = true
        )
        val inspectionTask = Task(
            id = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
            description = "Осуществить закупку кретически важного оборудования на базу в соответствии с текущеми нуждами предприятия",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025, 4, 15),
            status = TaskStatus.ON_APPROVAL,
            priority = TaskPriority.COMMON,
            destination = "г. Оренбург, ул. Сенная 2а",
            executor = employee2,
            inspector = employee1,
            shouldBeInspected = true
        )

        executionTasksList = mutableListOf(executionTask1, executionTask2)
        inspectingTasksList = mutableListOf(inspectionTask)
    }


    override suspend fun getAllExecutorTasks(): List<Task> {
        return executionTasksList
    }

    override suspend fun getTaskComments(taskId: String): List<Comment> {
        val list: MutableList<Task> = mutableListOf()
        list.addAll(executionTasksList)
        list.addAll(inspectingTasksList)
        return list.first { it.id == taskId }.comments ?: listOf()
    }

    override suspend fun getAllInspectorTasks(): List<Task> {
        return inspectingTasksList
    }

    override suspend fun createTask(task: Task): Boolean {
        inspectingTasksList.add(task)
        return true
    }

    override suspend fun createTaskComment(comment: Comment): Boolean {
        return true
    }

    override suspend fun getTaskById(taskId: String): Task {
        val list: MutableList<Task> = mutableListOf()
        list.addAll(executionTasksList)
        list.addAll(inspectingTasksList)
        return list.first { it.id == taskId }
    }

    override suspend fun approveTask(taskId: String): Task {
        TODO("Not yet implemented")
    }

    override suspend fun acceptTask(taskId: String): Task {
        TODO("Not yet implemented")
    }

    override suspend fun cancelTask(taskId: String): Task {
        TODO("Not yet implemented")
    }

    override suspend fun rejectTask(taskId: String): Task {
        TODO("Not yet implemented")
    }

    override suspend fun submitTask(taskId: String): Task {
        TODO("Not yet implemented")
    }
}