package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.AccessApiService
import com.petproject.workflow.data.network.mappers.AccessMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Access
import com.petproject.workflow.domain.repositories.AccessRepository
import java.io.IOException
import javax.inject.Inject

class AccessRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val accessMapper: AccessMapper,
    private val accessApiService: AccessApiService
): AccessRepository {
    override suspend fun getAccessById(id: String): Access {
        val response = accessApiService.getAccessById(id)
        if (response.isSuccessful) {
            response.body()?.let { return accessMapper.mapDtoToEntity(it) }
        }
        throw IOException()
    }

    override suspend fun getAllAccessesByIssuerId(issuerId: String): List<Access> {
        val response = accessApiService.getAllAccessesByIssuerId(issuerId)
        return response.map { accessMapper.mapDtoToEntity(it) }
    }

    override suspend fun getAllCurrentAccesses(): List<Access> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = accessApiService.getAllAccessesByHolderId(employeeId)
        return response.map { accessMapper.mapDtoToEntity(it) }
    }
}