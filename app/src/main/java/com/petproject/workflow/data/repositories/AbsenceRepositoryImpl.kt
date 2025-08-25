package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.AbsenceApiService
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.network.mappers.AbsenceMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.repositories.AbsenceRepository
import javax.inject.Inject

class AbsenceRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val absenceMapper: AbsenceMapper,
    private val absenceApiService: AbsenceApiService
) : AbsenceRepository {

    override suspend fun getAllAbsence(): List<Absence> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = absenceApiService.getAllAbsence(employeeId)
        return response.map { absenceMapper.mapDtoToEntity(it) }
    }

    override suspend fun getAbsence(id: String): Absence {
        val response = absenceApiService.getAbsence(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return absenceMapper.mapDtoToEntity(it)
            }
        }
        throw AuthException()
    }
}