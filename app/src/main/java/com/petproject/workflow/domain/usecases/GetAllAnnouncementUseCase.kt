package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AnnouncementRepository
import javax.inject.Inject

class GetAllAnnouncementUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke() = repository.getAllAnnouncement()
}