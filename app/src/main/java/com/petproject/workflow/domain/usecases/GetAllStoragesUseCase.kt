package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.StorageRepository
import javax.inject.Inject

class GetAllStoragesUseCase @Inject constructor(
    private val repository: StorageRepository
) {
    suspend operator fun invoke() = repository.getAllStorages()
}