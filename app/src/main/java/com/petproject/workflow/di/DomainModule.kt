package com.petproject.workflow.di

import com.petproject.workflow.data.repositories.AuthorizationRepositoryImpl
import com.petproject.workflow.data.repositories.EmployeeRepositoryImpl
import com.petproject.workflow.data.repositories.TaskRepositoryImpl
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import com.petproject.workflow.domain.repositories.EmployeeRepository
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
    fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}