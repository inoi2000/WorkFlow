package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.usecases.GetStorageByIdUseCase
import javax.inject.Inject

class StorageInfoViewModel @Inject constructor(
    private val storageId: String,
    private val getStorageByIdUseCase: GetStorageByIdUseCase
) : ViewModel() {
}