package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AnnouncementRepository
import javax.inject.Inject

data class DeleteAnnouncementFileUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(fileKeyId: String) =
        repository.deleteFile(fileKeyId)
}