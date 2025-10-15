package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Absence

interface AbsenceRepository {

    suspend fun getAllCurrentAbsence(): List<Absence>

    suspend fun getAbsence(id: String): Absence
}