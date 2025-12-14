package com.petproject.workflow.domain.usecases

import android.net.Uri
import com.petproject.workflow.domain.repositories.AnnouncementRepository
import javax.inject.Inject

data class UploadAnnouncementFileUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(uri: Uri) = repository.uploadFile(uri)
}