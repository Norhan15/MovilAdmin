package com.example.movil_admin.add.data.repository

import android.content.Context
import android.net.Uri
import com.example.movil_admin.add.data.model.request.CreateExampleRequest
import com.example.movil_admin.add.data.model.request.CreatePackRequest
import com.example.movil_admin.add.data.model.response.CreateExampleResponse
import com.example.movil_admin.add.data.model.response.CreatePackResponse
import com.example.movil_admin.core.network.RetrofitHelper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddRepository() {
    private val service = RetrofitHelper.addService

    suspend fun createPack(user: CreatePackRequest): Result<CreatePackResponse> {
        return try {
            val response = service.createPack(user)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createExample(example: CreateExampleRequest, context: Context): Result<CreateExampleResponse> {
        val nameBody = example.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val linkBody = example.link.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageAsFile = example.image?.let { uriToFile(it, context) }
        // Convertir el archivo a RequestBody y crear MultipartBody.Part
        val requestFile = imageAsFile?.asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("image", imageAsFile?.name, requestFile!!)

        return try {
            val response = service.createExample(nameBody, linkBody, image)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("No se pudo abrir la Uri")
        val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }

}