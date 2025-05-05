package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AnnouncementRepository
import javax.inject.Inject

class GetAnnouncementUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(id: String) = repository.getAnnouncement(id)
}