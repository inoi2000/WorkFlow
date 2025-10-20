package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.usecases.GetAllCurrentAbsenceUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AbsenceViewModel @Inject constructor(
    private val getAllCurrentAbsenceUseCase: GetAllCurrentAbsenceUseCase
) : ViewModel() {

    sealed class AbsenceListUiState {
        object Loading : AbsenceListUiState()
        data class Success(val absences: List<Absence>) : AbsenceListUiState()
        data class Error(val message: String) : AbsenceListUiState()
    }

    private val _uiState = MutableLiveData<AbsenceListUiState>(AbsenceListUiState.Loading)
    val uiState: MutableLiveData<AbsenceListUiState> get() = _uiState

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

    // Текущие активные фильтры
    private var currentSearchQuery: String = ""
    private var currentActiveFilter: AbsenceType? = null

    private var allAbsences: List<Absence> = emptyList()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.value = AbsenceListUiState.Loading
        viewModelScope.launch {
            try {
                val absences = getAllCurrentAbsenceUseCase.invoke()
                allAbsences = absences
                _absenceList.value = absences
                applyFilters()
                _uiState.value = AbsenceListUiState.Success(absences)
            } catch (e: Exception) {
                _uiState.value = AbsenceListUiState.Error(
                    "Не удалось загрузить список отсутствий: ${e.localizedMessage}"
                )
                _absenceList.value = emptyList()
                _filteredAbsenceList.value = emptyList()
            }
        }
    }

    fun retry() {
        loadData()
    }

    fun clearAllFilters() {
        currentSearchQuery = ""
        currentActiveFilter = null
        applyFilters()
    }

    fun filteredAbsenceListByDefault() {
        currentActiveFilter = null
        applyFilters()
    }

    fun filteredAbsenceListByType(type: AbsenceType) {
        currentActiveFilter = type
        applyFilters()
    }

    fun searchAbsences(query: String) {
        currentSearchQuery = query
        applyFilters()
    }

    private fun applyFilters() {
        var filtered = allAbsences.asSequence()

        // Apply search filter
        if (currentSearchQuery.isNotBlank()) {
//            filtered = filtered.filter { absence ->
//                absence.employee?.name?.contains(currentSearchQuery, true) == true ||
//                        absence.reason?.contains(currentSearchQuery, true) == true ||
//                        absence.destination?.contains(currentSearchQuery, true) == true
//            }
        }

        // Apply type filter
        currentActiveFilter?.let { type ->
            filtered = filtered.filter { it.type == type }
        }

        val result = filtered.toList()
        _filteredAbsenceList.value = result

        // Update UI state based on filtered results
        if (result.isEmpty() && (currentSearchQuery.isNotBlank() || currentActiveFilter != null)) {
            _uiState.value = AbsenceListUiState.Success(emptyList())
        } else if (result.isNotEmpty()) {
            _uiState.value = AbsenceListUiState.Success(result)
        }
    }

    // Методы для определения текущего активного фильтра (для выделения карточек во Fragment)
    fun getActiveAbsenceType(): AbsenceType? {
        return currentActiveFilter
    }

    fun getTitleForAbsenceType(type: AbsenceType?): String {
        return when (type) {
            AbsenceType.VACATION -> "Отпуска"
            AbsenceType.BUSINESS_TRIP -> "Командировки"
            AbsenceType.SICK_LEAVE -> "Больничные"
            AbsenceType.DAY_OFF -> "Отгулы"
            null -> "Все отсутствия"
        }
    }
}