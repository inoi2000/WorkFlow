package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.usecases.GetTaskCommentsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskCommentListViewModel @Inject constructor(
    private val taskId: String,
    private val getTaskCommentsUseCase: GetTaskCommentsUseCase
): ViewModel() {

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    val commentsCount: LiveData<String> get() = comments.map { it.count().toString() }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _comments.value = getTaskCommentsUseCase(taskId)
        }
    }
}