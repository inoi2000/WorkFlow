package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.usecases.GetJourneyByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class JourneyInfoViewModel @Inject constructor(
    private val journeyId: String,
    private val getJourneyByIdUseCase: GetJourneyByIdUseCase,
    val requestManager: RequestManager
) : ViewModel() {

    private val _journey = MutableLiveData<Journey>()
    val journey: LiveData<Journey> get() = _journey

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _journey.value = getJourneyByIdUseCase(journeyId)
        }
    }
}