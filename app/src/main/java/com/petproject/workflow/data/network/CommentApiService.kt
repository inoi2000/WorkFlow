package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.CommentDto
import com.petproject.workflow.domain.entities.FileKey
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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

    @Multipart
    @POST("/api/comments/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<FileKey>

    @DELETE("/api/comments/file_keys/{fileKeyId}")
    suspend fun deleteFile(
        @Path("fileKeyId") fileKeyId: String
    ): Response<Boolean>

    companion object {
        private const val QUERY_PARAM_TASK_ID = "task_id"
    }
}