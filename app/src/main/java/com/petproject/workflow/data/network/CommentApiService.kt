package com.petproject.workflow.data.network

import com.petproject.workflow.domain.entities.Comment
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentApiService {

    @GET("api/comments")
    suspend fun getAllCommentsByTask(
        @Query(QUERY_PARAM_TASK_ID) taskId: String
    ): List<Comment>

    companion object {
        private const val QUERY_PARAM_TASK_ID = "task_id"
    }
}