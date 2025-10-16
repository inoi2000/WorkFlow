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
): ViewModel() {

    private val _journeyList = MutableLiveData<List<Journey>>()
    val journeyList: LiveData<List<Journey>> get() = _journeyList

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _journeyList.value = getAllCurrentJourneysUseCase()
        }
    }
}