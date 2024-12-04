package com.petproject.workflow.domain.repositories

interface AuthorizationRepository {

    fun signIn(email: String, password: String, onFailureListener: (Exception) -> Unit)

    fun signOut()

    fun verifySuccessAuthorization(callback: (String?) -> Unit)
}
