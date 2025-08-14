package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.TaskApiService
import com.petproject.workflow.data.network.mappers.TaskMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val taskMapper: TaskMapper,
    private val taskApiService: TaskApiService,
) : TaskRepository {

    override suspend fun getAllExecutorTasks(): List<Task> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = taskApiService.getAllTasksByExecutor(employeeId)
        return response.map { taskMapper.mapDtoToEntity(it) }
    }

    override suspend fun getTaskComments(taskId: String): List<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllInspectorTasks(): List<Task> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = taskApiService.getAllTasksByInspector(employeeId)
        return response.map { taskMapper.mapDtoToEntity(it) }
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