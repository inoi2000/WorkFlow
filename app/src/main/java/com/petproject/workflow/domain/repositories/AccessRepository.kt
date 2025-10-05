package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Access

interface AccessRepository {

    suspend fun getAccessById(id: String): Access

    suspend fun getAllAccessesByIssuerId(issuerId: String): List<Access>

    suspend fun getAllCurrentAccesses(): List<Access>
}