package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.entities.AbsenceType
import javax.inject.Inject

class ServiceListViewModel @Inject constructor() :ViewModel() {

    private val _navigateToAbsenceListScreen = MutableLiveData<AbsenceType?>()
    val navigateToAbsenceListScreen: LiveData<AbsenceType?> get() = _navigateToAbsenceListScreen

    fun navigateToAbsenceListScreen(type: AbsenceType) {
        _navigateToAbsenceListScreen.value = type
    }

    fun onAbsenceScreenNavigated() {
        _navigateToAbsenceListScreen.value = null
    }
}