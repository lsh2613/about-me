package com.aboutme.app.file.service.helper

import com.aboutme.app.file.service.util.FileManager
import com.aboutme.common.extension.logger
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

class FileManagerTest : DescribeSpec({
    val log = logger()
    val mockFile =
        MockMultipartFile(
            "test-file.txt",
            "Hello, World!".toByteArray(),
        )
    val basePath = Path.of("test-upload")
    val filePath = basePath.resolve("parent/test-file.txt")

    afterEach {
        if (basePath.exists()) {
            Files.walk(basePath).use { paths ->
                paths.sorted(Comparator.reverseOrder())
                    .forEach(Files::deleteIfExists)
            }
            log.info("Deleted test base directory: {}", basePath)
        }
    }

    describe("디렉토리 생성") {
        context("존재하지 않는 디렉토리를 생성하면") {
            FileManager.mkdirsIfNotExists(filePath.parent)
            it("디렉토리가 생성된다") {
                filePath.parent.exists() shouldBe true
            }
        }
        describe("이미 디렉토리가 존재할 때") {
            Files.createDirectories(filePath.parent)
            context("디렉토리를 생성하려고 하면") {
                FileManager.mkdirsIfNotExists(filePath.parent)
                it("예외가 발생하지 않고 정상적으로 처리된다") {
                    filePath.parent.exists() shouldBe true
                }
            }
        }

        context("'.'이 포함된 이름으로 디렉토리를 생성하려고 하면") {
            val invalidDirPath = basePath.resolve("invalid.dir")
            it("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    FileManager.mkdirsIfNotExists(invalidDirPath)
                }
            }
        }

        context("빈문자열의 이름으로 디렉토리를 생성하려고 하면") {
            it("IllegalArgumentException이 발생한다") {
                val invalidPath = Path.of("")
                shouldThrow<IllegalArgumentException> {
                    FileManager.mkdirsIfNotExists(invalidPath)
                }
            }
        }
    }

    describe("파일 업로드") {
        context("파일 업로드 요청을 하면") {
            FileManager.uploadOrThrow(mockFile, filePath)
            it("파일이 업로드된다") {
                filePath.exists() shouldBe true
            }
        }
    }

    describe("파일/디렉토리 삭제") {
        context("파일을 삭제하면") {
            Files.createDirectories(filePath.parent)
            Files.write(filePath, mockFile.bytes)
            FileManager.deleteIfExists(filePath)
            it("파일이 삭제된다") {
                filePath.exists() shouldBe false
            }
        }

        context("디렉토리를 삭제하면") {
            Files.createDirectories(filePath.parent)
            Files.write(filePath, mockFile.bytes)
            FileManager.deleteIfExists(filePath.parent)
            it("디렉토리가 삭제된다") {
                filePath.parent.exists() shouldBe false
            }
        }

        context("파일이 존재하지 않으면") {
            FileManager.deleteIfExists(filePath)
            it("예외가 발생하지 않고 정상적으로 처리된다") {
                filePath.exists() shouldBe false
            }
        }
    }
})
