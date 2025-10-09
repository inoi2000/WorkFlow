package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Instruction
import com.petproject.workflow.domain.usecases.GetAllCurrentInstructionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructionListViewModel @Inject constructor(
    private val getAllCurrentInstructionsUseCase: GetAllCurrentInstructionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<InstructionListUiState>(InstructionListUiState.Loading)
    val uiState: StateFlow<InstructionListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = InstructionListUiState.Loading
            try {
                val instructions = getAllCurrentInstructionsUseCase()
                _uiState.value = InstructionListUiState.Success(instructions)
            } catch (e: Exception) {
                _uiState.value = InstructionListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun retry() {
        loadData()
    }

    sealed class InstructionListUiState {
        object Loading : InstructionListUiState()
        data class Success(val instructions: List<Instruction>) : InstructionListUiState()
        data class Error(val message: String) : InstructionListUiState()
    }
}