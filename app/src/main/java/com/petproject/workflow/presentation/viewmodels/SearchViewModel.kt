package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val query: String,
) : ViewModel() {
}