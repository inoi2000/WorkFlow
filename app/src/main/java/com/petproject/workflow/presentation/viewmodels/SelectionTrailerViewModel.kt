package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.usecases.GetAllTrailersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectionTrailerViewModel @Inject constructor(
    private val getAllTrailersUseCase: GetAllTrailersUseCase
) : ViewModel() {

    private val _trailerList = MutableLiveData<List<Trailer>>()
    val trailerList: LiveData<List<Trailer>> get() = _trailerList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    init {
        loadData()
    }

    fun loadData() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                val trailer = getAllTrailersUseCase()
                _trailerList.value = trailer
            } catch (e: Exception) {
                _errorState.value = "Ошибка загрузки прицепов"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun refreshData() {
        loadData()
    }
}