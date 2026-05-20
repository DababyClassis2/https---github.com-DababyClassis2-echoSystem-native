package com.echosystem.localshare.transfer

import com.echosystem.localshare.model.TransferDirection
import com.echosystem.localshare.model.TransferSession
import com.echosystem.localshare.model.TransferState
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.RandomAccessFile
import javax.inject.Inject

class ChunkedDownloader @Inject constructor(private val client: HttpClient) {
    fun download(sourceIp: String, sourcePort: Int, fileId: String, fileName: String, destDir: File): Flow<TransferSession> = flow {
        val tmpFile = File(destDir, "$fileName.tmp")
        val raf = RandomAccessFile(tmpFile, "rw")
        val resumeOffset = if (tmpFile.exists()) tmpFile.length() else 0L
        raf.seek(resumeOffset)

        // GET with Range
        client.prepareGet("http://$sourceIp:$sourcePort/files/$fileId/download") {
            header(HttpHeaders.Range, "bytes=$resumeOffset-")
        }.execute { response ->
            val channel = response.bodyAsChannel()
            while (!channel.isClosedForRead) {
                val buffer = ByteArray(512 * 1024)
                val read = channel.readAvailable(buffer)
                if (read > 0) {
                    raf.write(buffer, 0, read)
                    // emit TransferSession progress
                }
            }
        }
        raf.close()
        tmpFile.renameTo(File(destDir, fileName))
        // emit COMPLETE
    }
}
