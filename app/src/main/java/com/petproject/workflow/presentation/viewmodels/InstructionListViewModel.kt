package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Instruction
import com.petproject.workflow.domain.usecases.GetAllCurrentInstructionsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructionListViewModel @Inject constructor(
    private val getAllCurrentInstructionsUseCase: GetAllCurrentInstructionsUseCase
): ViewModel() {

    private val _instructionList = MutableLiveData<List<Instruction>>()
    val instructionList: LiveData<List<Instruction>> get() = _instructionList

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _instructionList.value = getAllCurrentInstructionsUseCase()
        }
    }
}