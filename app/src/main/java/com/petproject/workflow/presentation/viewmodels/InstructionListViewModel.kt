package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.usecases.GetAllCurrentInstructionsUseCase
import javax.inject.Inject

class InstructionListViewModel @Inject constructor(
    private val getAllCurrentInstructionsUseCase: GetAllCurrentInstructionsUseCase
): ViewModel() {
}