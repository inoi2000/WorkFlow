package com.petproject.workflow.presentation.viewmodels

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.CommentStatus
import com.petproject.workflow.domain.entities.FileKey
import com.petproject.workflow.domain.usecases.CreateCommentUseCase
import com.petproject.workflow.domain.usecases.DeleteCommentFileUseCase
import com.petproject.workflow.domain.usecases.UploadCommentFileUseCase
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateTaskCommentViewModel @Inject constructor(
    private val taskId: String,
    private val createCommentUseCase: CreateCommentUseCase,
    private val uploadCommentFileUseCase: UploadCommentFileUseCase,
    private val deleteCommentFileUseCase: DeleteCommentFileUseCase
) : ViewModel() {

    private val _uploadState = MutableLiveData<FileUploadState>(FileUploadState.Idle)
    val uploadState: LiveData<FileUploadState?> = _uploadState

    private val _uploadedFileKeys = MutableLiveData<MutableList<FileKey>>(mutableListOf())
    val uploadedFileKeys: LiveData<MutableList<FileKey>> = _uploadedFileKeys

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
                taskId = taskId,
                fileKeys = _uploadedFileKeys.value
            )
            val isSuccess = createCommentUseCase(comment)
            _isCommentCreated.value = isSuccess
        }
    }

    fun uploadFile(uri: Uri) {
        viewModelScope.launch {
            _uploadState.value = FileUploadState.Loading

            try {
                val fileKey = uploadCommentFileUseCase(uri)
                // Обновляем список загруженных файлов
                _uploadedFileKeys.value?.add(fileKey)
                _uploadState.value = FileUploadState.Success(fileKey.key)
            } catch (e: Exception) {
                _uploadState.value = FileUploadState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    sealed class FileUploadState {
        object Idle : FileUploadState()
        object Loading : FileUploadState()
        data class Success(val fileKey: String) : FileUploadState()
        data class Error(val message: String) : FileUploadState()
    }

}