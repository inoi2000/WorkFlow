package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.usecases.GetAllAnnouncementUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnnouncementListViewModel @Inject constructor(
    private val getAllAnnouncementUseCase: GetAllAnnouncementUseCase,
    val requestManager: RequestManager
): ViewModel() {

    private val _announcementList = MutableLiveData<List<Announcement>>()
    val announcementList: LiveData<List<Announcement>> get() = _announcementList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    init {
        loadData()
    }

    fun loadData() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                val announcements = getAllAnnouncementUseCase()
                _announcementList.value = announcements
                _loadingState.value = false
            } catch (e: Exception) {
                _errorState.value = "Не удалось загрузить данные"
                _loadingState.value = false
            }
        }
    }

    fun refreshData() {
        loadData()
    }
}