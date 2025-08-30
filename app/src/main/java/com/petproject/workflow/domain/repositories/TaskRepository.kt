package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.Task

interface TaskRepository {
    suspend fun getTaskById(taskId: String): Task

    suspend fun getAllExecutorTasks(): List<Task>

    suspend fun getAllInspectorTasks(): List<Task>

    suspend fun getTaskComments(taskId: String): List<Comment>

    suspend fun createTask(task: Task): Boolean

    suspend fun createTaskComment(comment: Comment): Boolean

    suspend fun acceptTask(taskId: String): Task

    suspend fun submitTask(taskId: String): Task

    suspend fun approveTask(taskId: String): Task

    suspend fun rejectTask(taskId: String): Task

    suspend fun cancelTask(taskId: String): Task
}