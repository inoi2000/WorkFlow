package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.usecases.GetTrailerByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrailerInfoViewModel @Inject constructor(
    private val trailerId: String,
    private val getTrailerByIdUseCase: GetTrailerByIdUseCase
): ViewModel() {

    private val _trailer = MutableLiveData<Trailer>()
    val trailer: LiveData<Trailer> get() = _trailer

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _trailer.value = getTrailerByIdUseCase(trailerId)
        }
    }
}