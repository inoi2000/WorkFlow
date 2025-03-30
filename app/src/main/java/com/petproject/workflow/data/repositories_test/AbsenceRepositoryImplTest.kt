package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.repositories.AbsenceRepository
import java.time.LocalDate
import javax.inject.Inject

class AbsenceRepositoryImplTest @Inject constructor() : AbsenceRepository {

    private val absenceList: List<Absence>

    init {
        val vacation = Absence(
            type = AbsenceType.VACATION,
            start = LocalDate.now(),
            end = LocalDate.now(),
            place = "Анапа",
            isApproval = true
        )
        val businessTrip = Absence(
            type = AbsenceType.BUSINESS_TRIP,
            start = LocalDate.now(),
            end = LocalDate.now(),
            place = "Екатеренбург",
            isApproval = true
        )
        val dayOff = Absence(
            type = AbsenceType.DAY_OFF,
            start = LocalDate.now(),
            end = LocalDate.now(),
            isApproval = true
        )
        val sickLeave = Absence(
            type = AbsenceType.SICK_LEAVE,
            start = LocalDate.now(),
            end = LocalDate.now(),
            isApproval = true
        )

        absenceList = listOf(vacation, businessTrip, dayOff, sickLeave)
    }

    override suspend fun getAllAbsence(): List<Absence> {
        return absenceList
    }
}