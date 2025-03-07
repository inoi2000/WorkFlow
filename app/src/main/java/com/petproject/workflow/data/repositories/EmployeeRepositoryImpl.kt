package com.petproject.workflow.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.mappers.EmployeeMapper
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.repositories.EmployeeRepository

class EmployeeRepositoryImpl : EmployeeRepository {

    private val employeeMapper = EmployeeMapper
    private val mainApiService = ApiFactory.mainApiService

    override suspend fun getEmployee(id: String): Employee {
        //TODO добавить обновления пользователя через websocket
        val response = mainApiService.getEmployee(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return employeeMapper.mapDtoToEntity(it)
            }
        }
        throw RuntimeException()
    }
}