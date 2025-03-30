package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.usecases.GetAllAbsenceUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AbsenceViewModel @Inject constructor(
    private val getAllAbsenceUseCase: GetAllAbsenceUseCase
) : ViewModel() {

    private val _absenceList = MutableLiveData<List<Absence>>()

    private val _filteredAbsenceList = MutableLiveData<List<Absence>>()
    val filteredAbsenceList: LiveData<List<Absence>> get() = _filteredAbsenceList

    val vacationCount: LiveData<Int> = _absenceList.map { taskList ->
        taskList.count { it.type == AbsenceType.VACATION }
    }

    val businessTripCount: LiveData<Int> = _absenceList.map { taskList ->
        taskList.count { it.type == AbsenceType.BUSINESS_TRIP }
    }

    val sickLeaveCount: LiveData<Int> = _absenceList.map { taskList ->
        taskList.count { it.type == AbsenceType.SICK_LEAVE }
    }

    val dayOffCount: LiveData<Int> = _absenceList.map { taskList ->
        taskList.count { it.type == AbsenceType.DAY_OFF }
    }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _absenceList.value = getAllAbsenceUseCase.invoke()
            filteredAbsenceListByDefault()
        }
    }

    fun filteredAbsenceListByDefault() {
        _filteredAbsenceList.value = _absenceList.value
    }

    fun filteredAbsenceListByType(type: AbsenceType) {
        _filteredAbsenceList.value = _absenceList.value?.filter { it.type == type }
    }
}