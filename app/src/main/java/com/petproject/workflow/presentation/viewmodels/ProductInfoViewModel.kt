package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.usecases.GetProductByIdUseCase
import javax.inject.Inject

class ProductInfoViewModel @Inject constructor(
    private val productId: String,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
}