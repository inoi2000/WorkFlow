package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Access
import com.petproject.workflow.domain.usecases.GetAllCurrentAccessesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccessListViewModel @Inject constructor(
    private val getAllCurrentAccessesUseCase: GetAllCurrentAccessesUseCase
): ViewModel() {

    private val _accessList = MutableLiveData<List<Access>>()
    val accessList: LiveData<List<Access>> get() = _accessList

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _accessList.value = getAllCurrentAccessesUseCase()
        }
    }
}