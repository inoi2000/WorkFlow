package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.AnnouncementApiService
import com.petproject.workflow.data.network.mappers.AnnouncementMapper
import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.repositories.AnnouncementRepository
import okio.IOException
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    private val announcementMapper: AnnouncementMapper,
    private val announcementApiService: AnnouncementApiService
) : AnnouncementRepository {

    override suspend fun getAllAnnouncement(): List<Announcement> {
        val response = announcementApiService.getAllAnnouncements()
        return response.map { announcementMapper.mapDtoToEntity(it) }
    }

    override suspend fun getAnnouncement(id: String): Announcement {
        val response = announcementApiService.getAnnouncement(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return announcementMapper.mapDtoToEntity(it)
            }
        }
        throw IOException()
    }
}