package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.usecases.GetAllAnnouncementUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnnouncementViewModel @Inject constructor(
    private val getAllAnnouncementUseCase: GetAllAnnouncementUseCase
): ViewModel() {

    private val _announcementList = MutableLiveData<List<Announcement>>()
    val announcementList: LiveData<List<Announcement>> get() = _announcementList

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _announcementList.value = getAllAnnouncementUseCase()
        }
    }
}