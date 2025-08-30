package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.CommentDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentApiService {

    @GET("api/comments")
    suspend fun getAllCommentsByTask(
        @Query(QUERY_PARAM_TASK_ID) taskId: String
    ): List<CommentDto>

    @POST("/api/comments")
    suspend fun createComment(
        @Body comment: CommentDto
    ): Response<CommentDto>

    companion object {
        private const val QUERY_PARAM_TASK_ID = "task_id"
    }
}