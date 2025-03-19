package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.repositories.TaskRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImplTest @Inject constructor(
    private val tokenManager: TokenManager
) : TaskRepository {

    override suspend fun getAllExecutingTasks(): List<Task> {
        val executionTask1 = Task(
            id = UUID.randomUUID().toString(),
            description = "Распечатать журналы инструкатажа по пожарной безопасности за 2025 год",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,5,10),
            status = TaskStatus.NEW,
            executor = null,
            inspector = null
        )
        val executionTask2 = Task(
            id = UUID.randomUUID().toString(),
            description = "Подготовить документацию по проекту сдачи недвижимого имущесто в субаренду в соответствии с текущеми контрактами",
            creation = LocalDate.now(),
            deadline = LocalDate.of(2025,4,15),
            status = TaskStatus.NEW,
            executor = null,
            inspector = null
        )
        return listOf(executionTask1, executionTask2)
    }
}