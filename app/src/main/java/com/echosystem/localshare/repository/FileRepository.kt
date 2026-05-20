package com.echosystem.localshare.repository

import android.content.Context
import android.webkit.MimeTypeMap
import com.echosystem.localshare.model.SharedFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile
import java.net.URLDecoder
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepository @Inject constructor(@ApplicationContext context: Context) {

    private val receivedDir = File(context.getExternalFilesDir(null), "LocalShare/received")
    private val tempDir = File(context.cacheDir, "localshare_uploads_tmp")
    
    private val _files = MutableStateFlow<List<SharedFile>>(emptyList())
    val filesFlow: StateFlow<List<SharedFile>> = _files.asStateFlow()
    private val activeSessions = ConcurrentHashMap<String, RandomAccessFile>()

    init {
        receivedDir.mkdirs()
        tempDir.mkdirs()
    }

    fun listFiles(): List<SharedFile> = _files.value

    fun getFile(id: String): SharedFile? = _files.value.find { it.id == id }

    fun getFileAsJavaFile(id: String): File? = getFile(id)?.let { File(it.absolutePath) }

    suspend fun saveMultipartFile(
        name: String,
        inputStream: InputStream,
        mimeType: String,
        fromDeviceId: String
    ): SharedFile = withContext(Dispatchers.IO) {
        val sanitizedName = sanitizeFileName(name)
        val file = File(receivedDir, sanitizedName)
        
        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output, bufferSize = 8192)
            }
        }
        
        val sharedFile = SharedFile(
            id = UUID.randomUUID().toString(),
            name = sanitizedName,
            absolutePath = file.absolutePath,
            mimeType = mimeType,
            fromDeviceId = fromDeviceId
        )
        
        _files.value = _files.value + sharedFile
        return@withContext sharedFile
    }

    fun getOrCreateSession(sessionId: String, fileName: String): RandomAccessFile {
        return activeSessions.getOrPut(sessionId) {
            RandomAccessFile(File(tempDir, "$sessionId.tmp"), "rw")
        }
    }

    fun getSessionReceivedBytes(sessionId: String, fileName: String): Long {
        val tmpFile = File(tempDir, "$sessionId.tmp")
        return if (tmpFile.exists()) tmpFile.length() else 0L
    }

    suspend fun finalizeSession(sessionId: String, fileName: String, fromDeviceId: String): SharedFile = withContext(Dispatchers.IO) {
        val tmpFile = File(tempDir, "$sessionId.tmp")
        val session = activeSessions.remove(sessionId)
        session?.close()
        
        val sanitizedName = sanitizeFileName(fileName)
        val destFile = File(receivedDir, sanitizedName)
        tmpFile.renameTo(destFile)
        
        val extension = MimeTypeMap.getFileExtensionFromUrl(destFile.absolutePath)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "application/octet-stream"
        
        val sharedFile = SharedFile(
            id = UUID.randomUUID().toString(),
            name = sanitizedName,
            absolutePath = destFile.absolutePath,
            mimeType = mimeType,
            fromDeviceId = fromDeviceId
        )
        
        _files.value = _files.value + sharedFile
        return@withContext sharedFile
    }

    fun deleteFile(id: String) {
        val file = _files.value.find { it.id == id }
        if (file != null) {
            File(file.absolutePath).delete()
            _files.value = _files.value.filter { it.id != id }
        }
    }

    private fun sanitizeFileName(name: String): String {
        val decoded = URLDecoder.decode(name, "UTF-8")
        val sanitized = decoded.replace("[^a-zA-Z0-9._\-]".toRegex(), "_")
        return if (sanitized.isBlank()) "file_${System.currentTimeMillis()}" else sanitized
    }
}
