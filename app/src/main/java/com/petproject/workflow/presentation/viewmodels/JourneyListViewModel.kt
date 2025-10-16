package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.usecases.GetAllCurrentJourneysUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class JourneyListViewModel @Inject constructor(
    private val getAllCurrentJourneysUseCase: GetAllCurrentJourneysUseCase
) : ViewModel() {

    private val _journeyList = MutableLiveData<List<Journey>>()
    val journeyList: LiveData<List<Journey>> get() = _journeyList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    init {
        loadData()
    }

    fun loadData() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val journeys = getAllCurrentJourneysUseCase()
                _journeyList.value = journeys
            } catch (e: Exception) {
                _errorMessage.value = "Не удалось загрузить список выездов: ${e.localizedMessage}"
                _journeyList.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshData() {
        loadData()
    }

    fun errorMessageShown() {
        _errorMessage.value = null
    }
}