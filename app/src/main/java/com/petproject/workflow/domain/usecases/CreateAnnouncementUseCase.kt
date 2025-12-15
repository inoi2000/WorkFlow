package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.repositories.AnnouncementRepository
import javax.inject.Inject

data class CreateAnnouncementUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(announcement: Announcement) =
        repository.createAnnouncement(announcement)
}