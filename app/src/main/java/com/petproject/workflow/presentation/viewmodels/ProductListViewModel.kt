package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Product
import com.petproject.workflow.domain.usecases.GetAllProductsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
): ViewModel() {

    sealed class ProductListUiState {
        object Loading : ProductListUiState()
        data class Success(val products: List<Product>) : ProductListUiState()
        data class Error(val message: String) : ProductListUiState()
    }

    private val _uiState = MutableLiveData<ProductListUiState>(
        ProductListUiState.Loading
    )
    val uiState: MutableLiveData<ProductListUiState> = _uiState

    private val _productList = MutableLiveData<List<Product>>()
    private val _filteredProductList = MutableLiveData<List<Product>>()

    val filteredProductList: LiveData<List<Product>> get() = _filteredProductList

    // Текущие активные фильтры
    private var currentSearchQuery: String = ""

    private var allProducts: List<Product> = emptyList()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.value = ProductListUiState.Loading
        viewModelScope.launch {
            try {
                val products = getAllProductsUseCase()
                allProducts = products
                _productList.value = products
                applyFilters()
                _uiState.value = ProductListUiState.Success(products)
            } catch (e: Exception) {
                _uiState.value = ProductListUiState.Error(
                    "Не удалось загрузить список нефтепродуктов: ${e.localizedMessage}"
                )
                _productList.value = emptyList()
                _filteredProductList.value = emptyList()
            }
        }
    }

    fun retry() {
        loadData()
    }

    fun clearAllFilters() {
        currentSearchQuery = ""
        applyFilters()
    }

    fun searchProduct(query: String) {
        currentSearchQuery = query
        applyFilters()
    }

    private fun applyFilters() {
        var filtered = allProducts.asSequence()

        if (currentSearchQuery.isNotBlank()) {
            filtered = filtered.filter { product ->
                product.name.contains(currentSearchQuery, true)
            }
        }

        val result = filtered.toList()
        _filteredProductList.value = result

        // Update UI state based on filtered results
        if (result.isEmpty() && (currentSearchQuery.isNotBlank())) {
            _uiState.value = ProductListUiState.Success(emptyList())
        } else if (result.isNotEmpty()) {
            _uiState.value = ProductListUiState.Success(result)
        }
    }
}