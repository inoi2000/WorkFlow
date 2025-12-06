package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Task

interface TaskRepository {
    suspend fun getTaskById(taskId: String): Task

    suspend fun getAllCurrentExecutorTasks(): List<Task>

    suspend fun getAllCurrentInspectorTasks(): List<Task>

    suspend fun createTask(task: Task): Boolean

    suspend fun acceptTask(taskId: String): Task

    suspend fun submitTask(taskId: String): Task

    suspend fun approveTask(taskId: String): Task

    suspend fun rejectTask(taskId: String): Task

    suspend fun cancelTask(taskId: String): Task
}