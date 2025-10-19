package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.CommentStatus
import com.petproject.workflow.domain.usecases.CreateTaskCommentUseCase
import com.petproject.workflow.domain.usecases.GetTaskByIdUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateTaskCommentViewModel @Inject constructor(
    private val taskId: String,
    private val createTaskCommentUseCase: CreateTaskCommentUseCase
) : ViewModel() {

    private val _errorInputCommentText = MutableLiveData(false)
    val errorInputCommentText: LiveData<Boolean> get() = _errorInputCommentText

    private val _isCommentCreated = MutableLiveData(false)
    val isCommentCreated: LiveData<Boolean> get() = _isCommentCreated

    var commentTextField: ObservableField<String> = ObservableField()

    fun createComment(text: String) {
        viewModelScope.launch {
            val comment = Comment(
                id = UUID.randomUUID().toString(),
                text = text,
                createdAt = LocalDateTime.now(),
                commentStatus = CommentStatus.INFORMATION,
                taskId = taskId
//                files = listOf()
            )
            val isSuccess = createTaskCommentUseCase(comment)
            _isCommentCreated.value = isSuccess
        }
    }

}