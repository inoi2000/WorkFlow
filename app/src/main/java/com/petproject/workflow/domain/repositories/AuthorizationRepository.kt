package com.petproject.workflow.domain.repositories

interface AuthorizationRepository {

    fun signIn(username: String, password: String, onFailureListener: (Exception) -> Unit)

    fun signOut()

    fun verifySuccessAuthorization(callback: (String?) -> Unit)
}
