package com.petproject.workflow.data.repositories

import android.net.Uri
import com.petproject.workflow.data.network.CommentApiService
import com.petproject.workflow.data.network.mappers.CommentMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.data.network.utils.FileUploadHelper
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.FileKey
import com.petproject.workflow.domain.repositories.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val fileUploadHelper: FileUploadHelper,
    private val commentMapper: CommentMapper,
    private val commentApiService: CommentApiService,
) : CommentRepository {

    override suspend fun getTaskComments(taskId: String): List<Comment> {
        return commentApiService
            .getAllCommentsByTask(taskId)
            .map { commentMapper.mapDtoToEntity(it) }
    }

    override suspend fun createComment(comment: Comment): Boolean {
        val dto = commentMapper.mapEntityToDto(comment)
        val response = commentApiService.createComment(dto)
        return response.isSuccessful
    }

    override suspend fun uploadFile(uri: Uri): FileKey {
        return try {
            // 1. Создаем MultipartBody.Part из Uri
            val filePart = fileUploadHelper.createMultipartPart(uri)
                ?: throw IllegalArgumentException("Не удалось создать файл для загрузки")

            // 2. Отправляем файл на сервер
            val response = commentApiService.uploadFile(filePart)

            // 3. Проверяем успешность запроса
            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Пустой ответ от сервера")
            } else {
                throw IllegalStateException("Ошибка сервера: ${response.code()}")
            }
        } catch (e: Exception) {
            // 4. Обрабатываем ошибки
            throw RuntimeException("Ошибка загрузки файла: ${e.message}", e)
        }
    }

    override suspend fun deleteFile(fileKeyId: String): Boolean {
        return commentApiService.deleteFile(fileKeyId).isSuccessful
    }


}