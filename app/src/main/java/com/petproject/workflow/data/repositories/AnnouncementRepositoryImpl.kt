package com.petproject.workflow.data.repositories

import android.net.Uri
import com.petproject.workflow.data.network.AnnouncementApiService
import com.petproject.workflow.data.network.mappers.AnnouncementMapper
import com.petproject.workflow.data.network.utils.FileUploadHelper
import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.entities.FileKey
import com.petproject.workflow.domain.repositories.AnnouncementRepository
import okio.IOException
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    private val announcementMapper: AnnouncementMapper,
    private val announcementApiService: AnnouncementApiService,
    private val fileUploadHelper: FileUploadHelper
) : AnnouncementRepository {

    override suspend fun createAnnouncement(announcement: Announcement): Boolean {
        val dto = announcementMapper.mapEntityToDto(announcement)
        val response = announcementApiService.createAnnouncement(dto)
        return response.isSuccessful
    }

    override suspend fun getAllAnnouncement(): List<Announcement> {
        val response = announcementApiService.getAllAnnouncements()
        return response.map { announcementMapper.mapDtoToEntity(it) }
    }

    override suspend fun getAnnouncement(id: String): Announcement {
        val response = announcementApiService.getAnnouncement(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return announcementMapper.mapDtoToEntity(it)
            }
        }
        throw IOException()
    }

    override suspend fun uploadFile(uri: Uri): FileKey {
        return try {
            // 1. Создаем MultipartBody.Part из Uri
            val filePart = fileUploadHelper.createMultipartPart(uri)
                ?: throw IllegalArgumentException("Не удалось создать файл для загрузки")

            // 2. Отправляем файл на сервер
            val response = announcementApiService.uploadFile(filePart)

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
        return announcementApiService.deleteFile(fileKeyId).isSuccessful
    }
}