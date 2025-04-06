package com.example.movil_admin.core.provider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MediaProvider(private val context: Context) {
    private var tempImageUri: Uri? = null

    // Crea un archivo temporal para guardar la imagen capturada
    private fun createTempImageFile(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir("images")
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)

        return FileProvider.getUriForFile(
            context,
            "com.example.movil_admin.core.provider.MediaProvider",
            imageFile
        ).also { tempImageUri = it }
    }

    fun getImageCaptureUri(): Uri {
        return createTempImageFile()
    }

    fun getTempImageUri(): Uri? {
        return tempImageUri
    }

    fun cleanupTempFiles() {
        tempImageUri?.let { uri ->
            val file = uri.path?.let { File(it) }
            file?.delete()
        }
    }
}
