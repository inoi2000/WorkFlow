package com.petproject.workflow.domain.repositories

interface AuthorizationRepository {

    fun signIn(email: String, password: String)

    fun signOut()
}
