package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StorageEventListViewModel @Inject constructor(
    private val storageId: String,
) : ViewModel() {
}