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
import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.CommentStatus
import com.petproject.workflow.domain.entities.FileKey
import com.petproject.workflow.domain.usecases.CreateAnnouncementUseCase
import com.petproject.workflow.domain.usecases.DeleteAnnouncementFileUseCase
import com.petproject.workflow.domain.usecases.UploadAnnouncementFileUseCase
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateAnnouncementViewModel @Inject constructor(
    private val createAnnouncementUseCase: CreateAnnouncementUseCase,
    private val uploadAnnouncementFileUseCase: UploadAnnouncementFileUseCase,
    private val deleteAnnouncementFileUseCase: DeleteAnnouncementFileUseCase
): ViewModel() {

    private val _uploadState = MutableLiveData<FileUploadState>(FileUploadState.Idle)
    val uploadState: LiveData<FileUploadState> = _uploadState

    private val _uploadedFileKey = MutableLiveData<FileKey?>()
    val uploadedFileKey: LiveData<FileKey?> = _uploadedFileKey

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isAnnouncementCreated = MutableLiveData(false)
    val isAnnouncementCreated: LiveData<Boolean> = _isAnnouncementCreated

    val announcementTitleTextField = ObservableField("")
    val announcementContentTextField = ObservableField("")
    val hasAttachedFile = ObservableBoolean(false)

    fun createAnnouncement() {
        if (announcementTitleTextField.get().isNullOrBlank()) {
            _errorMessage.value = "Титул объявления не может быть пустым"
            return
        }
        if (announcementContentTextField.get().isNullOrBlank()) {
            _errorMessage.value = "Текст объявления не может быть пустым"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val announcement = Announcement(
                    id = UUID.randomUUID().toString(),
                    title = announcementTitleTextField.get()?.trim() ?: "",
                    createdAt = LocalDateTime.now(),
                    content = announcementContentTextField.get()?.trim() ?: "",
                    fileKey = _uploadedFileKey.value
                )

                val isSuccess = createAnnouncementUseCase(announcement)
                _isAnnouncementCreated.value = isSuccess

                if (!isSuccess) {
                    _errorMessage.value = "Не удалось создать объявление"
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
                val fileKey = uploadAnnouncementFileUseCase(uri)
                _uploadedFileKey.value = fileKey

                hasAttachedFile.set(true)

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
                _uploadedFileKey.value = null

                hasAttachedFile.set(false)

                deleteAnnouncementFileUseCase(fileKey.id)
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