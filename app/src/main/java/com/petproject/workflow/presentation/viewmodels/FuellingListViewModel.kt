package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Fuelling
import com.petproject.workflow.domain.usecases.GetAllFuellingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class FuellingListViewModel @Inject constructor(
    private val getAllFuellingsUseCase: GetAllFuellingsUseCase
) : ViewModel() {

    sealed class FuellingListUiState {
        object Loading : FuellingListUiState()
        data class Success(val fuellings: List<Fuelling>) : FuellingListUiState()
        data class Error(val message: String) : FuellingListUiState()
    }

    private val _uiState = MutableStateFlow<FuellingListUiState>(FuellingListUiState.Loading)
    val uiState: StateFlow<FuellingListUiState> = _uiState.asStateFlow()

    private val _showCalendar = MutableStateFlow(false)
    val showCalendar: StateFlow<Boolean> = _showCalendar.asStateFlow()

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    private var allFuellings: List<Fuelling> = emptyList()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = FuellingListUiState.Loading

            try {
                val fuellings = getAllFuellingsUseCase()
                allFuellings = fuellings
                applyFilters()
            } catch (e: Exception) {
                _uiState.value = FuellingListUiState.Error(
                    "Не удалось загрузить список заправок: ${e.localizedMessage}"
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
        val filteredFuellings = _selectedDate.value?.let { selectedDate ->
            allFuellings.filter { fuelling ->
                fuelling.createdAt.toLocalDate() == selectedDate
            }
        } ?: allFuellings

        _uiState.value = FuellingListUiState.Success(filteredFuellings)
    }

    fun retry() {
        loadData()
    }
}