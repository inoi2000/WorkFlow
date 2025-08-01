package com.petproject.workflow.domain.repositories

import android.content.Intent
import com.petproject.workflow.domain.entities.Role

interface AuthorizationRepository {

    fun startAuthFlow() : Intent

    suspend fun handleAuthResponseIntent(
        intent: Intent,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    )

    fun getLogoutPageIntent(): Intent

    suspend fun signOut()

    suspend fun verifySuccessAuthorization(callback: (String?) -> Unit)

    suspend fun getRole() : Role
}
