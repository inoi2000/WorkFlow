package com.petproject.workflow.domain.repositories

import android.net.Uri
import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.entities.FileKey

interface AnnouncementRepository {

    suspend fun createAnnouncement(announcement: Announcement): Boolean

    suspend fun getAllAnnouncement(): List<Announcement>

    suspend fun getAnnouncement(id: String): Announcement

    suspend fun uploadFile(uri: Uri): FileKey

    suspend fun deleteFile(fileKeyId: String): Boolean
}