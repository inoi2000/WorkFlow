package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.usecases.GetAllCurrentAbsenceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
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

    private val _uiState = MutableStateFlow<AbsenceListUiState>(AbsenceListUiState.Loading)
    val uiState: StateFlow<AbsenceListUiState> get() = _uiState

    private val allAbsencesFlow = MutableStateFlow<List<Absence>>(emptyList())
    private val searchQueryFlow = MutableStateFlow("")
    private val activeFilterFlow = MutableStateFlow<AbsenceType?>(null)

    // Комбинируем все фильтры
    val filteredAbsenceList = combine(
        allAbsencesFlow,
        searchQueryFlow,
        activeFilterFlow
    ) { absences, query, filter ->
        absences
            .filter { absence ->
                // Фильтр по типу
                filter?.let { absence.policy.type == it } ?: true
            }
            .filter { absence ->
                // Поиск по имени сотрудника
                query.isBlank() || absence.employee.name.contains(query, ignoreCase = true)
            }
    }

    // Счетчики для карточек
    val vacationCount = allAbsencesFlow.map { absences ->
        absences.count { it.policy.type == AbsenceType.VACATION }
    }.asLiveData()

    val businessTripCount = allAbsencesFlow.map { absences ->
        absences.count { it.policy.type == AbsenceType.BUSINESS_TRIP }
    }.asLiveData()

    val sickLeaveCount = allAbsencesFlow.map { absences ->
        absences.count { it.policy.type == AbsenceType.SICK_LEAVE }
    }.asLiveData()

    val dayOffCount = allAbsencesFlow.map { absences ->
        absences.count { it.policy.type == AbsenceType.DAY_OFF }
    }.asLiveData()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.value = AbsenceListUiState.Loading
        viewModelScope.launch {
            try {
                val absences = getAllCurrentAbsenceUseCase.invoke()
                allAbsencesFlow.value = absences
                _uiState.value = AbsenceListUiState.Success(absences)
            } catch (e: Exception) {
                _uiState.value = AbsenceListUiState.Error(
                    "Не удалось загрузить список отсутствий: ${e.localizedMessage}"
                )
                allAbsencesFlow.value = emptyList()
            }
        }
    }

    fun retry() {
        loadData()
    }

    fun clearAllFilters() {
        searchQueryFlow.value = ""
        activeFilterFlow.value = null
    }

    fun filteredAbsenceListByType(type: AbsenceType) {
        activeFilterFlow.value = type
    }

    fun searchAbsences(query: String) {
        searchQueryFlow.value = query
    }

    // Методы для получения текущего состояния
    fun getActiveAbsenceType(): AbsenceType? {
        return activeFilterFlow.value
    }

    fun getCurrentSearchQuery(): String {
        return searchQueryFlow.value
    }
}