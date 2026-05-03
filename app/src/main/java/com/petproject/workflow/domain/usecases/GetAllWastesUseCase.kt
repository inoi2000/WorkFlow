package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.WasteRepository
import javax.inject.Inject

class GetAllWastesUseCase @Inject constructor(
    private val repository: WasteRepository
) {
    suspend operator fun invoke() = repository.getAllWastes()
}