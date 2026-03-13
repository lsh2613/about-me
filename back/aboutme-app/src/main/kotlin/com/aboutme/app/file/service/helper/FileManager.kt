package com.aboutme.app.file.service.helper

import com.aboutme.common.extension.logger
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import kotlin.io.path.isRegularFile

class FileManager {
    companion object {
        private val fileDeleter = FileDeleter()
        private val log = logger()

        fun upload(
            file: MultipartFile,
            filePath: Path,
        ) {
            runCatching {
                mkdirsIfNotExists(filePath.parent)
                file.transferTo(filePath)
            }.onSuccess {
                log.info("File uploaded to: {}", filePath)
            }.onFailure { e ->
                log.error("Failed to upload file: {}", filePath, e)
            }
        }

        fun replaceFile(
            filePath: Path,
            file: MultipartFile,
        ) {
            require(filePath.isRegularFile()) { "파일 경로는 일반 파일이어야 합니다: $filePath" }
            Files.copy(file.inputStream, filePath, REPLACE_EXISTING)
            deleteIfExists(filePath)
            upload(file, filePath)
        }

        fun mkdirsIfNotExists(dirName: Path) {
            val file = dirName.toFile()
            require(!file.name.contains(".")) { "'.'이 포함되지 않은 디렉토리만 생성 가능합니다: $dirName" }
            require(!file.name.isEmpty()) { "빈 문자열은 디렉토리 이름으로 사용할 수 없습니다: $dirName" }

            if (!file.exists() && !file.mkdirs()) {
                log.error("Failed to create file: {}", dirName)
            }

            log.info("Directory created to: {}", dirName)
        }

        fun deleteIfExists(path: Path) {
            runCatching {
                if (Files.isDirectory(path)) {
                    Files.walkFileTree(path, fileDeleter)
                } else {
                    Files.deleteIfExists(path)
                }
            }.onSuccess {
                log.info("Directory deleted to: {}", path)
            }.onFailure { e ->
                log.error("Failed to delete file: {}", path, e)
            }
        }
    }
}
