package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.utils.TokensManager
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tokensManager: TokensManager
) : TaskRepository {

    override suspend fun getAllExecutorTasks(): List<Task> {
        val token = tokensManager.getAccessToken()
        if (token != null) {
            val empId = TokensManager.getIdFromAccessToken(token)
            TODO("Добавить реальную реализацию")
        } else {
            throw AuthException()
        }
    }

    override suspend fun getTaskComments(taskId: String): List<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllInspectorTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun createTask(task: Task): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun createTaskComment(comment: Comment): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: String): Task {
        TODO("Not yet implemented")
    }
}