package com.petproject.workflow.data.network.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class FileUploadHelper @Inject constructor(
    private val context: Context
) {
    fun createMultipartPart(
        uri: Uri,
        partName: String = "file"
    ): MultipartBody.Part? {
        return try {
            val file = uriToFile(uri)
            val requestFile = file.asRequestBody(
                context.contentResolver.getType(uri)?.toMediaTypeOrNull()
                    ?: "multipart/form-data".toMediaTypeOrNull()
            )

            MultipartBody.Part.createFormData(partName, file.name, requestFile)
        } catch (e: Exception) {
            null
        }
    }

    private fun uriToFile(uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileName = getFileName(uri)
        val file = File.createTempFile(
            "upload_${System.currentTimeMillis()}",
            "_$fileName",
            context.cacheDir
        )

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        return file
    }

    private fun getFileName(uri: Uri): String {
        var name = ""
        val projection = arrayOf(OpenableColumns.DISPLAY_NAME)

        try {
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                    name = cursor.getString(columnIndex)
                }
            }
        } catch (e: Exception) {
            // Если не удалось получить имя через OpenableColumns, используем fallback
            e.printStackTrace()
        }

        // Fallback: используем последний сегмент URI
        if (name.isEmpty()) {
            name = uri.lastPathSegment ?: "file"
            // Убираем параметры запроса если они есть
            val questionMarkIndex = name.indexOf('?')
            if (questionMarkIndex != -1) {
                name = name.substring(0, questionMarkIndex)
            }
        }

        return name
    }
}