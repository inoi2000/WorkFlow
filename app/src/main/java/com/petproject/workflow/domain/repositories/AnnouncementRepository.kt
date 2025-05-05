package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Announcement

interface AnnouncementRepository {

    suspend fun getAllAnnouncement(): List<Announcement>

    suspend fun getAnnouncement(id: String): Announcement
}