package com.echosystem.localshare.transfer

import com.echosystem.localshare.model.TransferDirection
import com.echosystem.localshare.model.TransferSession
import com.echosystem.localshare.model.TransferState
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class ChunkedUploader @Inject constructor(private val client: HttpClient) {
    fun upload(targetIp: String, targetPort: Int, file: File, sessionId: String): Flow<TransferSession> = flow {
        val totalBytes = file.length()
        var transferredBytes = 0L
        var retryCount = 0

        // 1. HEAD to check resume
        // 2. Loop and PUT chunks
        
        // Simplified upload logic
        while (transferredBytes < totalBytes) {
            try {
                val chunkSize = minOf(4 * 1024 * 1024, (totalBytes - transferredBytes).toInt())
                // val response = client.put(...)
                // update transferredBytes, emit TransferSession
            } catch (e: Exception) {
                if (retryCount++ < 3) {
                    delay(retryCount * 1000L)
                } else {
                    // emit FAILED
                    break
                }
            }
        }
        // emit COMPLETE
    }
}
