package com.petproject.workflow.di

import com.petproject.workflow.data.repositories.AbsenceRepositoryImpl
import com.petproject.workflow.data.repositories.AccessRepositoryImpl
import com.petproject.workflow.data.repositories.AnnouncementRepositoryImpl
import com.petproject.workflow.data.repositories.AuthorizationRepositoryImpl
import com.petproject.workflow.data.repositories.EmployeeRepositoryImpl
import com.petproject.workflow.data.repositories.InstructionRepositoryImpl
import com.petproject.workflow.data.repositories.TaskRepositoryImpl
import com.petproject.workflow.domain.repositories.AbsenceRepository
import com.petproject.workflow.domain.repositories.AccessRepository
import com.petproject.workflow.domain.repositories.AnnouncementRepository
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import com.petproject.workflow.domain.repositories.EmployeeRepository
import com.petproject.workflow.domain.repositories.InstructionRepository
import com.petproject.workflow.domain.repositories.TaskRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindAuthorizationRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    fun bindEmployeeRepository(impl: EmployeeRepositoryImpl): EmployeeRepository

    @Binds
    fun bindAccessRepository(impl: AccessRepositoryImpl): AccessRepository

    @Binds
    fun bindInstructionRepository(impl: InstructionRepositoryImpl): InstructionRepository

    @Binds
    fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    fun bindAbsenceRepository(impl: AbsenceRepositoryImpl): AbsenceRepository

    @Binds
    fun bindAnnouncementRepository(impl: AnnouncementRepositoryImpl): AnnouncementRepository
}