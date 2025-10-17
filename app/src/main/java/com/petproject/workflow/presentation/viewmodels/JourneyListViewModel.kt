package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.usecases.GetAllCurrentJourneysUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class JourneyListViewModel @Inject constructor(
    private val getAllCurrentJourneysUseCase: GetAllCurrentJourneysUseCase
) : ViewModel() {

    sealed class JourneyListUiState {
        object Loading : JourneyListUiState()
        data class Success(val journeys: List<Journey>) : JourneyListUiState()
        data class Error(val message: String) : JourneyListUiState()
    }

    private val _uiState = MutableStateFlow<JourneyListUiState>(JourneyListUiState.Loading)
    val uiState: StateFlow<JourneyListUiState> = _uiState.asStateFlow()

    private val _showCalendar = MutableStateFlow(false)
    val showCalendar: StateFlow<Boolean> = _showCalendar.asStateFlow()

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    private var allJourneys: List<Journey> = emptyList()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = JourneyListUiState.Loading

            try {
                val journeys = getAllCurrentJourneysUseCase()
                allJourneys = journeys
                applyFilters()
            } catch (e: Exception) {
                _uiState.value = JourneyListUiState.Error(
                    "Не удалось загрузить список выездов: ${e.localizedMessage}"
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
        val filteredJourneys = _selectedDate.value?.let { selectedDate ->
            allJourneys.filter { journey ->
                journey.statement?.destinationTime?.toLocalDate() == selectedDate
            }
        } ?: allJourneys

        _uiState.value = JourneyListUiState.Success(filteredJourneys)
    }

    fun retry() {
        loadData()
    }
}