package com.example.movil_admin.core.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors

class MediaProvider(private val context: Context) {
    private var tempImageUri: Uri? = null
    private val executor = Executors.newSingleThreadExecutor()

    // Crea un archivo temporal para guardar la imagen capturada
    private fun createTempImageFile(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"

        // Asegurarse de que el directorio existe
        val storageDir = context.getExternalFilesDir("images")
        if (storageDir?.exists() != true) {
            storageDir?.mkdirs()
        }

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

    // Esta función se debe llamar después de capturar la imagen para redimensionarla
    fun processAndResizeCapturedImage(onComplete: (Uri?) -> Unit) {
        val uri = tempImageUri
        if (uri == null) {
            onComplete(null)
            return
        }

        executor.execute {
            try {
                // Abrir el input stream desde el URI
                val inputStream = context.contentResolver.openInputStream(uri)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (originalBitmap == null) {
                    Log.e("MediaProvider", "No se pudo decodificar la imagen")
                    onComplete(null)
                    return@execute
                }

                // Calcular dimensiones para mantener la relación de aspecto
                val width = originalBitmap.width
                val height = originalBitmap.height
                val targetSize = 900

                val scaleWidth = targetSize.toFloat() / width
                val scaleHeight = targetSize.toFloat() / height
                val scale = scaleWidth.coerceAtMost(scaleHeight)

                val matrix = Matrix()
                matrix.postScale(scale, scale)

                // Crear bitmap redimensionado
                val resizedBitmap = Bitmap.createBitmap(
                    originalBitmap, 0, 0, width, height, matrix, true
                )

                // Guardar el bitmap redimensionado en el mismo archivo
                val file = uri.path?.let { File(it) } ?: run {
                    Log.e("MediaProvider", "No se pudo obtener la ruta del archivo")
                    onComplete(null)
                    return@execute
                }

                val outStream = FileOutputStream(file)
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 95, outStream)
                outStream.flush()
                outStream.close()

                // Liberar memoria
                originalBitmap.recycle()
                resizedBitmap.recycle()

                // Notificar completado en el hilo principal
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    onComplete(uri)
                }

            } catch (e: Exception) {
                Log.e("MediaProvider", "Error al procesar la imagen: ${e.message}")
                e.printStackTrace()
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    onComplete(null)
                }
            }
        }
    }

    fun cleanupTempFiles() {
        tempImageUri?.let { uri ->
            try {
                val path = uri.path
                if (path != null) {
                    val file = File(path)
                    if (file.exists()) {
                        file.delete()
                    }
                }
            } catch (e: Exception) {
                Log.e("MediaProvider", "Error al eliminar archivo temporal: ${e.message}")
            }
        }
    }
}
