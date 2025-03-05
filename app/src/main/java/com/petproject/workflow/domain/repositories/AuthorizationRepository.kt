package com.petproject.workflow.domain.repositories

interface AuthorizationRepository {

    suspend fun signIn(username: String, password: String, onFailureListener: (Exception) -> Unit)

    suspend fun signOut()

    suspend fun verifySuccessAuthorization(callback: (String?) -> Unit)
}
