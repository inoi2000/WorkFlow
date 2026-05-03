package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.WasteRepository
import javax.inject.Inject

class GetWasteByIdUseCase @Inject constructor(
    private val repository: WasteRepository
) {
    suspend operator fun invoke(id: String) = repository.getWasteById(id)
}