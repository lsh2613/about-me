package com.aboutme.app.file.service.helper

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class FileDeleter : SimpleFileVisitor<Path>() {
    override fun visitFile(
        file: Path,
        attrs: BasicFileAttributes,
    ): FileVisitResult {
        Files.delete(file)
        return FileVisitResult.CONTINUE
    }

    override fun postVisitDirectory(
        dir: Path,
        exc: IOException?,
    ): FileVisitResult {
        Files.delete(dir)
        return FileVisitResult.CONTINUE
    }
}
