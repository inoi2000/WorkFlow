package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Storage

interface StorageRepository {

    suspend fun getAllStorages(): List<Storage>

    suspend fun getStorageById(id: String): Storage
}