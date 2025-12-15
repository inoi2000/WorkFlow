package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.AnnouncementDto
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

interface AnnouncementApiService {

    @GET("api/announcements")
    suspend fun getAllAnnouncements(): List<AnnouncementDto>

    @GET("api/announcements/{id}")
    suspend fun getAnnouncement(
        @Path("id") announcementId: String
    ): Response<AnnouncementDto>

    @POST("/api/announcements")
    suspend fun createAnnouncement(
        @Body announcement: AnnouncementDto
    ): Response<AnnouncementDto>

    @Multipart
    @POST("/api/announcements/posters/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<FileKey>

    @DELETE("/api/announcements/file_keys/{fileKeyId}")
    suspend fun deleteFile(
        @Path("fileKeyId") fileKeyId: String
    ): Response<Boolean>
}