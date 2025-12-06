package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.TaskApiService
import com.petproject.workflow.data.network.mappers.TaskMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.repositories.TaskRepository
import java.io.IOException
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val taskMapper: TaskMapper,
    private val taskApiService: TaskApiService,
) : TaskRepository {

    override suspend fun getAllCurrentExecutorTasks(): List<Task> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = taskApiService.getAllTasksByExecutor(employeeId)
        return response.map { taskMapper.mapDtoToEntity(it) }
    }

    override suspend fun getAllCurrentInspectorTasks(): List<Task> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = taskApiService.getAllTasksByInspector(employeeId)
        return response.map { taskMapper.mapDtoToEntity(it) }
    }

    override suspend fun createTask(task: Task): Boolean {
        val taskDto = taskMapper.mapEntityToDto(task)
        val response = taskApiService.createTask(taskDto)
        return response.isSuccessful
    }

    override suspend fun getTaskById(taskId: String): Task {
        val response = taskApiService.getTask(taskId)
        if (response.isSuccessful) {
            response.body()?.let { return taskMapper.mapDtoToEntity(it) }
        }
        throw IOException()
    }

    override suspend fun acceptTask(taskId: String): Task {
        val response = taskApiService.acceptTask(taskId)
        if (response.isSuccessful) {
            response.body()?.let { return  taskMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun submitTask(taskId: String): Task {
        val response = taskApiService.submitTask(taskId)
        if (response.isSuccessful) {
            response.body()?.let { return  taskMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun approveTask(taskId: String): Task {
        val response = taskApiService.approvalTask(taskId)
        if (response.isSuccessful) {
            response.body()?.let { return  taskMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun rejectTask(taskId: String): Task {
        val response = taskApiService.rejectTask(taskId)
        if (response.isSuccessful) {
            response.body()?.let { return  taskMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun cancelTask(taskId: String): Task {
        val response = taskApiService.cancelTask(taskId)
        if (response.isSuccessful) {
            response.body()?.let { return  taskMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}