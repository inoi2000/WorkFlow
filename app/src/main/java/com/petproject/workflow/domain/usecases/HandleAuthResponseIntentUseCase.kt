package com.petproject.workflow.domain.usecases

import android.content.Intent
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class HandleAuthResponseIntentUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(
        intent: Intent,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) = repository.handleAuthResponseIntent(intent, onSuccessListener, onFailureListener)
}