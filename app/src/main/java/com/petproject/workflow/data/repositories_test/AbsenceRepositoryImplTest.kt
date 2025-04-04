package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceStatus
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.repositories.AbsenceRepository
import java.time.LocalDate
import javax.inject.Inject

class AbsenceRepositoryImplTest @Inject constructor() : AbsenceRepository {

    private val absenceList: List<Absence>

    init {
        val vacation = Absence(
            id = "550e8400-e29b-41d4-a716-446655440000",
            type = AbsenceType.VACATION,
            status = AbsenceStatus.APPROVED,
            start = LocalDate.now(),
            end = LocalDate.now(),
            place = "Анапа",
            isApproval = true
        )
        val businessTrip = Absence(
            id = "00112233-4455-6677-8899-aabbccddeeff",
            type = AbsenceType.BUSINESS_TRIP,
            status = AbsenceStatus.APPROVED,
            start = LocalDate.now(),
            end = LocalDate.now(),
            place = "Екатеренбург",
            isApproval = true
        )
        val dayOff = Absence(
            type = AbsenceType.DAY_OFF,
            status = AbsenceStatus.APPROVED,
            start = LocalDate.now(),
            end = LocalDate.now(),
            isApproval = true
        )
        val sickLeave = Absence(
            type = AbsenceType.SICK_LEAVE,
            status = AbsenceStatus.APPROVED,
            start = LocalDate.now(),
            end = LocalDate.now(),
            isApproval = true
        )

        absenceList = listOf(vacation, businessTrip, dayOff, sickLeave)
    }

    override suspend fun getAllAbsence(): List<Absence> {
        return absenceList
    }

    override suspend fun getAbsence(id: String): Absence {
        return absenceList.first { it.id == id }
    }
}