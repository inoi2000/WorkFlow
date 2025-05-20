package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Role

interface AuthorizationRepository {

    suspend fun signIn(username: String, password: String, onFailureListener: (Exception) -> Unit)

    suspend fun signOut()

    suspend fun verifySuccessAuthorization(callback: (String?) -> Unit)

    suspend fun getRole() : Role
}
