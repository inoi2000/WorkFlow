package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.TaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {
    @GET("api/tasks/{id}")
    suspend fun getTask(
        @Path("id") taskId: String
    ): Response<TaskDto>

    @GET("api/tasks/executors/{executorId}")
    suspend fun getAllTasksByExecutor(
        @Path("executorId") executorId: String
    ): List<TaskDto>

    @GET("api/tasks/inspectors/{inspectorId}")
    suspend fun getAllTasksByInspector(
        @Path("inspectorId") inspectorId: String
    ): List<TaskDto>

    @POST("/api/tasks")
    suspend fun createTask(
        @Body taskDto: TaskDto
    ): Response<TaskDto>
}