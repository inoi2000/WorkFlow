package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.CommentApiService
import com.petproject.workflow.data.network.TaskApiService
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.mappers.TaskMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.repositories.EmployeeRepository
import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val taskMapper: TaskMapper,
    private val taskApiService: TaskApiService,
    private val commentApiService: CommentApiService,
    private val employeeRepository: EmployeeRepository
) : TaskRepository {

    override suspend fun getAllExecutorTasks(): List<Task> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = taskApiService.getAllTasksByExecutor(employeeId)
        return response.map { dto ->
            val inspector = employeeRepository.getEmployee(dto.executorId ?: throw RuntimeException())
            taskMapper.mapDtoToEntity(
                dto,
                executor = null,
                inspector = inspector)
        }
    }

    override suspend fun getTaskComments(taskId: String): List<Comment> {
        return commentApiService.getAllCommentsByTask(taskId)
    }

    override suspend fun getAllInspectorTasks(): List<Task> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = taskApiService.getAllTasksByInspector(employeeId)
        return response.map { dto ->
            val executor = employeeRepository.getEmployee(dto.executorId ?: throw RuntimeException())
            taskMapper.mapDtoToEntity(
                dto,
                executor = executor,
                inspector = null)
        }
    }

    override suspend fun createTask(task: Task): Boolean {
        val taskDto = taskMapper.mapEntityToDto(task)
        val response = taskApiService.createTask(taskDto)
        return response.isSuccessful
    }

    override suspend fun createTaskComment(comment: Comment): Boolean {
        val response = commentApiService.createComment(comment)
        return response.isSuccessful
    }

    override suspend fun getTaskById(taskId: String): Task {
        val response = taskApiService.getTask(taskId)
        if (response.isSuccessful) {
            response.body()?.let {
                return taskMapper.mapDtoToEntity(it, null, null)
            }
        }
        throw AuthException()
    }
}