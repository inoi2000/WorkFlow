package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.usecases.GetAllProductsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
): ViewModel() {



    fun loadData() {
        viewModelScope.launch {
        }
    }

    fun retry() {
        loadData()
    }

}