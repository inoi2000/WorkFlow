package com.petproject.workflow.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.mappers.EmployeeMapper
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.repositories.EmployeeRepository
import kotlinx.coroutines.runBlocking

class EmployeeRepositoryImpl : EmployeeRepository {

    private val employeeMapper = EmployeeMapper
    private val mainApiService = ApiFactory.mainApiService

    private val employee = MutableLiveData<Employee>()

    override fun getEmployee(id: String): LiveData<Employee> {
        //TODO добавить обновления пользователя через websocket
        runBlocking {
            val response = mainApiService.getEmployee(id)
            if (response.isSuccessful) {
                val employeeDto = response.body()?.let {
                    val entity = employeeMapper.mapDtoToEntity(it)
                    employee.value = entity
                }
            }
        }
        return employee
    }
}