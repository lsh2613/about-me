package com.aboutme.app.file.service.helper

import com.aboutme.common.annotation.Helper
import com.aboutme.core.file.domain.FileUploadType
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.multipart.MultipartFile
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.nio.file.Path
import java.util.UUID

@Helper
class FileNamer {
    @Value("\${file.upload.base-path}")
    private lateinit var uploadBasePath: String

    @Value("\${server.protocol}")
    private lateinit var protocol: String

    @Value("\${server.host}")
    private lateinit var host: String

    @Value("\${server.port}")
    private lateinit var port: String

    private lateinit var baseUrl: URL

    @PostConstruct
    fun init() {
        try {
            baseUrl = URL(protocol, host, port.toInt(), "")
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
    }

    fun toUri(filePath: Path): URI {
        try {
            return URL(baseUrl, filePath.toString()).toURI()
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
    }

    fun createUploadDirPath(
        type: FileUploadType,
        delimiterId: Long? = null,
    ): Path {
        return Path.of(uploadBasePath, type.subPath, delimiterId?.toString() ?: "")
    }

    fun createUploadFilePath(
        type: FileUploadType,
        delimiterId: Long? = null,
        file: MultipartFile,
    ): Path {
        val dirPath = createUploadDirPath(type, delimiterId)
        val fileName = createUUIDFileName(file)
        return dirPath.resolve(fileName)
    }

    private fun createUUIDFileName(file: MultipartFile): String {
        val uuid: String = UUID.randomUUID().toString()
        val extension = extractExtension(file)
        val newFileName = String.format("%s.%s", uuid, extension)
        return newFileName
    }

    private fun extractExtension(file: MultipartFile): String {
        val originalFilename = file.originalFilename!!
        return originalFilename.substringAfterLast('.', "")
    }
}
