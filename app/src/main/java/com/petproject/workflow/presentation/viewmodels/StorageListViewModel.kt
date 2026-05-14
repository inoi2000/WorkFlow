package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Storage
import com.petproject.workflow.domain.usecases.GetAllStoragesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class StorageListViewModel @Inject constructor(
    private val getAllStoragesUseCase: GetAllStoragesUseCase
) : ViewModel() {

    sealed class StorageListUiState {
        object Loading : StorageListUiState()
        data class Success(val storages: List<Storage>) : StorageListUiState()
        data class Error(val message: String) : StorageListUiState()
    }

    private val _uiState = MutableLiveData<StorageListUiState>(StorageListUiState.Loading)
    val uiState: MutableLiveData<StorageListUiState> = _uiState

    private val _storageList = MutableLiveData<List<Storage>>()
    private val _filteredStorageList = MutableLiveData<List<Storage>>()

    val filteredStorageList: LiveData<List<Storage>> get() = _filteredStorageList

    // Текущие активные фильтры
    private var currentSearchQuery: String = ""

    private var allStorages: List<Storage> = emptyList()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.value = StorageListUiState.Loading
        viewModelScope.launch {
            try {
                val storages = getAllStoragesUseCase()
                allStorages = storages
                _storageList.value = storages
                applyFilters()
                _uiState.value = StorageListUiState.Success(storages)
            } catch (e: Exception) {
                _uiState.value = StorageListUiState.Error(
                    "Не удалось загрузить список хранилищ: ${e.localizedMessage}"
                )
                _storageList.value = emptyList()
                _filteredStorageList.value = emptyList()
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

    fun searchStorage(query: String) {
        currentSearchQuery = query
        applyFilters()
    }

    private fun applyFilters() {
        var filtered = allStorages.asSequence()

        if (currentSearchQuery.isNotBlank()) {
            filtered = filtered.filter { storage ->
                storage.name.contains(currentSearchQuery, true) ||
                        storage.currentProduct?.name?.contains(
                            currentSearchQuery, true
                        ) == true
            }
        }

        val result = filtered.toList()
        _filteredStorageList.value = result

        // Update UI state based on filtered results
        if (result.isEmpty() && (currentSearchQuery.isNotBlank())) {
            _uiState.value = StorageListUiState.Success(emptyList())
        } else if (result.isNotEmpty()) {
            _uiState.value = StorageListUiState.Success(result)
        }
    }
}