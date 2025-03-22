package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Task

interface TaskRepository {
    suspend fun getAllExecutingTasks(): List<Task>

    suspend fun getExecutingTask(id: String): Task
}