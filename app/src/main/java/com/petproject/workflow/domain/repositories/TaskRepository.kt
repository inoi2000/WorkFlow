package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.Task

interface TaskRepository {
    suspend fun getAllExecutingTasks(): List<Task>

    suspend fun getExecutingTask(id: String): Task

    suspend fun getTaskComments(taskId: String): List<Comment>
}