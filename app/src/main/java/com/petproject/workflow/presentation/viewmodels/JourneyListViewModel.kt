package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.usecases.GetAllCurrentJourneysUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = JourneyListUiState.Loading

            try {
                val journeys = getAllCurrentJourneysUseCase()
                _uiState.value = JourneyListUiState.Success(journeys)
            } catch (e: Exception) {
                _uiState.value = JourneyListUiState.Error(
                    "Не удалось загрузить список выездов: ${e.localizedMessage}"
                )
            }
        }
    }

    fun retry() {
        loadData()
    }
}