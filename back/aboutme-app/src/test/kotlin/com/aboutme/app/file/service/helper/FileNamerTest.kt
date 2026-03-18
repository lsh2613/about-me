package com.aboutme.app.file.service.helper

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.web.multipart.MultipartFile

class FileNamerTest : DescribeSpec({

    val fileNamer = FileNamer()

    describe("파일 확장자 추출") {
        val file = mockk<MultipartFile>()

        context("파일명에 확장자가 없는 경우") {
            every { file.originalFilename } returns "file"
            it("에러를 반환한다") {
                shouldThrow<IllegalArgumentException> { fileNamer.extractExtension(file) }
            }
        }

        context("파일명에 확장자가 있는 경우") {
            every { file.originalFilename } returns "file.txt"
            it("확장자를 반환한다") {
                val extension = fileNamer.extractExtension(file)
                extension shouldBe "txt"
            }
        }
    }
})
