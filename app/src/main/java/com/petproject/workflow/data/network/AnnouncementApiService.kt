package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.AnnouncementDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnnouncementApiService {

    @GET("api/announcements")
    suspend fun getAllAnnouncements(): List<AnnouncementDto>

    @GET("api/announcements/{id}")
    suspend fun getAnnouncement(
        @Path("id") announcementId: String
    ): Response<AnnouncementDto>
}