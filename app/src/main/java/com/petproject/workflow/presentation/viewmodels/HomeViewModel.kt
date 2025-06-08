package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Role
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.usecases.GetAbsenceUseCase
import com.petproject.workflow.domain.usecases.GetEmployeeUseCase
import com.petproject.workflow.domain.usecases.GetRoleUseCase
import com.petproject.workflow.domain.usecases.GetTaskByIdUseCase
import com.petproject.workflow.domain.usecases.SignOutUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val employeeId: String,
    private val getRoleUseCase: GetRoleUseCase,
    private val getEmployeeUseCase: GetEmployeeUseCase,
    private val getAbsenceUseCase: GetAbsenceUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    private val _role = MutableLiveData<Role>()
    val role: LiveData<Role> get() = _role

    private val _navigateToLoginScreen = MutableLiveData(false)
    val navigateToLoginScreen: LiveData<Boolean> get() = _navigateToLoginScreen

    private val _employee = MutableLiveData<Employee>()
    val employee: LiveData<Employee> = _employee

    private val _vacation = MutableLiveData<Absence?>()
    val vacation: LiveData<Absence?> get() = _vacation

    private val _businessTrip = MutableLiveData<Absence?>()
    val businessTrip: LiveData<Absence?> get() = _businessTrip

    private val _executorTask = MutableLiveData<Task?>()
    val executorTask: LiveData<Task?> get() = _executorTask

    private val _inspectorTask = MutableLiveData<Task?>()
    val inspectorTask: LiveData<Task?> get() = _inspectorTask

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            if (throwable is AuthException) {
                runBlocking {
                    signOutUseCase()
                }
                _navigateToLoginScreen.value = true
            }
        }) {
            val role = getRoleUseCase()
            _role.value = role

            val employee = getEmployeeUseCase(employeeId)
            _employee.value = employee
            employee.tasks?.firstOrNull()?.id?.let {
                _executorTask.value = getTaskByIdUseCase(it)
            }
            employee.onApproval?.firstOrNull()?.id?.let {
                _inspectorTask.value = getTaskByIdUseCase(it)
            }
            employee.absences?.firstOrNull {
                it.type == AbsenceType.VACATION
            }?.id?.let {
                _vacation.value = getAbsenceUseCase(it)
            }
            employee.absences?.firstOrNull {
                it.type == AbsenceType.BUSINESS_TRIP
            }?.id?.let {
                _businessTrip.value = getAbsenceUseCase(it)
            }

        }
    }
}