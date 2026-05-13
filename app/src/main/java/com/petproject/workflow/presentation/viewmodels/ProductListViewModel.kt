package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.usecases.GetAllProductsUseCase
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
): ViewModel() {

}