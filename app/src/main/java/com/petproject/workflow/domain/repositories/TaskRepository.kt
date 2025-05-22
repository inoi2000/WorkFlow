package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.Task

interface TaskRepository {
    suspend fun getAllExecutorTasks(): List<Task>

    suspend fun getExecutorTask(id: String): Task

    suspend fun getAllInspectorTasks(): List<Task>

    suspend fun getInspectorTask(id: String): Task

    suspend fun getTaskComments(taskId: String): List<Comment>

    suspend fun createTask(task: Task): Boolean
}