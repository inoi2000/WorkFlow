package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.usecases.GetAllStatementsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class StatementListViewModel @Inject constructor(
    private val getAllStatementsUseCase: GetAllStatementsUseCase
): ViewModel() {

    sealed class StatementListUiState {
        object Loading : StatementListUiState()
        data class Success(val statements: List<Statement>) : StatementListUiState()
        data class Error(val message: String) : StatementListUiState()
    }

    private val _uiState = MutableStateFlow<StatementListUiState>(StatementListUiState.Loading)
    val uiState: StateFlow<StatementListUiState> = _uiState.asStateFlow()

    private val _showCalendar = MutableStateFlow(false)
    val showCalendar: StateFlow<Boolean> = _showCalendar.asStateFlow()

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    private var allStatements: List<Statement> = emptyList()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = StatementListUiState.Loading

            try {
                val statement = getAllStatementsUseCase()
                allStatements = statement
                applyFilters()
            } catch (e: Exception) {
                _uiState.value = StatementListUiState.Error(
                    "Не удалось загрузить список заявок: ${e.localizedMessage}"
                )
            }
        }
    }

    fun toggleCalendar() {
        _showCalendar.value = !_showCalendar.value
    }

    fun onDateSelected(dateMillis: Long) {
        val selectedDate = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        _selectedDate.value = selectedDate
        applyFilters()
    }

    fun clearFilter() {
        _selectedDate.value = null
        _showCalendar.value = false
        applyFilters()
    }

    private fun applyFilters() {
        val filteredStatements = _selectedDate.value?.let { selectedDate ->
            allStatements.filter { statement ->
                statement.destinationTime.toLocalDate() == selectedDate
            }
        } ?: allStatements

        _uiState.value = StatementListUiState.Success(filteredStatements)
    }

    fun retry() {
        loadData()
    }
}