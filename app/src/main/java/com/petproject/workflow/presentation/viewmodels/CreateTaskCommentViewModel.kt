package com.petproject.workflow.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
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
    val uploadState: LiveData<FileUploadState> = _uploadState

    private val _uploadedFileKeys = MutableLiveData<List<FileKey>>(emptyList())
    val uploadedFileKeys: LiveData<List<FileKey>> = _uploadedFileKeys

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isCommentCreated = MutableLiveData(false)
    val isCommentCreated: LiveData<Boolean> = _isCommentCreated

    val commentTextField = ObservableField("")
    val hasAttachedFiles = ObservableBoolean(false)
    val attachedFilesCount = ObservableInt(0)

    fun createComment(text: String) {
        if (text.isBlank()) {
            _errorMessage.value = "Комментарий не может быть пустым"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val comment = Comment(
                    id = UUID.randomUUID().toString(),
                    text = text.trim(),
                    createdAt = LocalDateTime.now(),
                    commentStatus = CommentStatus.INFORMATION,
                    taskId = taskId,
                    fileKeys = _uploadedFileKeys.value?.takeIf { it.isNotEmpty() }
                )

                val isSuccess = createCommentUseCase(comment)
                _isCommentCreated.value = isSuccess

                if (!isSuccess) {
                    _errorMessage.value = "Не удалось создать комментарий"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка: ${e.localizedMessage ?: "Неизвестная ошибка"}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadFile(uri: Uri) {
        viewModelScope.launch {
            _uploadState.value = FileUploadState.Loading
            _errorMessage.value = null

            try {
                val fileKey = uploadCommentFileUseCase(uri)

                val currentList = _uploadedFileKeys.value?.toMutableList() ?: mutableListOf()
                currentList.add(fileKey)
                _uploadedFileKeys.value = currentList

                // Обновляем Observable поля
                attachedFilesCount.set(currentList.size)
                hasAttachedFiles.set(currentList.isNotEmpty())

                _uploadState.value = FileUploadState.Success(fileKey.key)
            } catch (e: Exception) {
                _uploadState.value = FileUploadState.Error
                _errorMessage.value = "Ошибка загрузки файла: ${e.localizedMessage ?: "Неизвестная ошибка"}"
            }
        }
    }

    fun deleteFile(fileKey: FileKey) {
        viewModelScope.launch {
            try {
                val currentList = _uploadedFileKeys.value?.toMutableList() ?: mutableListOf()
                currentList.remove(fileKey)
                _uploadedFileKeys.value = currentList

                // Обновляем Observable поля
                attachedFilesCount.set(currentList.size)
                hasAttachedFiles.set(currentList.isNotEmpty())

                deleteCommentFileUseCase(fileKey.id)
            } catch (e: Exception) {
                Log.d("Error", "deleteFile: "+ e.message)
                // Логируем, но не показываем пользователю
            }
        }
    }

    sealed class FileUploadState {
        object Idle : FileUploadState()
        object Loading : FileUploadState()
        data class Success(val fileKey: String) : FileUploadState()
        object Error : FileUploadState()
    }
}