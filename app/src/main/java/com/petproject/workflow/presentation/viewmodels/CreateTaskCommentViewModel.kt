package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.usecases.GetTaskCommentsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTaskCommentViewModel @Inject constructor(
    private val taskId: String,
    private val getTaskCommentsUseCase: GetTaskCommentsUseCase
) : ViewModel() {

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    val commentsCount: String get() = comments.value?.count().toString()

    private val _errorInputCommentText = MutableLiveData(false)
    val errorInputCommentText: LiveData<Boolean> get() = _errorInputCommentText

    var commentTextField: ObservableField<String> = ObservableField()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _comments.value = getTaskCommentsUseCase(taskId)
        }
    }
}