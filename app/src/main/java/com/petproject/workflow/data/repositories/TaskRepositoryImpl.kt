package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager
) : TaskRepository {

    override suspend fun getAllExecutingTasks(): List<Task> {
        val token = tokenManager.getToken()
        if (token != null) {
            val empId = TokenManager.getIdFromToken(token)
            TODO("Добавить реальную реализацию")
        } else {
            throw AuthException()
        }
    }

    override suspend fun getExecutingTask(id: String): Task {
        TODO("Not yet implemented")
    }
}