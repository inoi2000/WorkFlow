package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.usecases.CreateTaskCommentUseCase
import com.petproject.workflow.domain.usecases.GetTaskByIdUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class CreateTaskCommentViewModel @Inject constructor(
    private val taskId: String,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val createTaskCommentUseCase: CreateTaskCommentUseCase
) : ViewModel() {

    private val _errorInputCommentText = MutableLiveData(false)
    val errorInputCommentText: LiveData<Boolean> get() = _errorInputCommentText

    private val _isCommentCreated = MutableLiveData(false)
    val isCommentCreated: LiveData<Boolean> get() = _isCommentCreated

    var commentTextField: ObservableField<String> = ObservableField()

    fun createComment(text: String) {
        viewModelScope.launch {
            val task = getTaskByIdUseCase(taskId)
            val comment = Comment(
                text = text,
                creation = LocalDate.now(),
                task = task,
                files = listOf()
            )
            val isSuccess = createTaskCommentUseCase(comment)
            _isCommentCreated.value = isSuccess
        }
    }

}