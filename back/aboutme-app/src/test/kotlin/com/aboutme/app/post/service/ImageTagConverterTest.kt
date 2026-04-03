package com.aboutme.app.post.service

import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.file.service.util.FileManager
import com.aboutme.app.post.service.helper.ImageTagConverter
import com.aboutme.core.file.domain.FileUploadType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.verify
import org.jsoup.Jsoup
import org.junit.jupiter.api.extension.ExtendWith
import java.io.ByteArrayInputStream
import java.net.URI
import java.nio.file.Path
import java.util.Base64

@ExtendWith(MockKExtension::class)
class ImageTagConverterTest : DescribeSpec({
    val fileNamer = mockk<FileNamer>(relaxed = true)
    val imageTagConverter = ImageTagConverter(fileNamer)

    val postId = 1L

    fun encoded(text: String) = Base64.getEncoder().encodeToString(text.toByteArray())

    beforeSpec {
        clearMocks(fileNamer)
        mockkObject(FileManager)

        every { fileNamer.createUploadDirPath(FileUploadType.POST, postId) } returns Path.of("test-dir")
        every { FileManager.upload(any<ByteArrayInputStream>(), any<Path>()) } just runs
    }

    describe("base64 이미지 태그 변환") {
        context("src가 data:image;base64 형식이라면") {
            val convertedUri =
                "https://aboutme.com/upload/converted.png".also {
                    every { fileNamer.toUri(any()) } returns URI(it)
                }
            val doc = Jsoup.parse("<p>본문</p><img src=\"data:image/png;base64,${encoded("image-data")}\" />")

            imageTagConverter.base64ToUri(doc, postId)
            it("업로드 후 src를 URI로 치환한다") {
                verify(exactly = 1) { fileNamer.createUploadDirPath(FileUploadType.POST, postId) }
                verify(exactly = 1) { FileManager.upload(any<ByteArrayInputStream>(), any<Path>()) }
                verify(exactly = 1) { fileNamer.toUri(any()) }
                doc.select("img").first()!!.attr("src") shouldBe convertedUri
            }
        }

        context("src가 base64 형식이 아니라면") {
            it("업로드하지 않고 src를 유지한다") {
                val nonBase64 = "https://aboutme.com/static/image.png"
                val doc = Jsoup.parse("<img src=\"$nonBase64\" />")

                imageTagConverter.base64ToUri(doc, postId)

                verify(exactly = 1) { fileNamer.createUploadDirPath(FileUploadType.POST, postId) }
                verify(exactly = 0) { FileManager.upload(any<ByteArrayInputStream>(), any<Path>()) }
                verify(exactly = 0) { fileNamer.toUri(any()) }
                doc.select("img").first()!!.attr("src") shouldBe nonBase64
            }
        }

        context("base64 데이터가 비어있거나 형식이 올바르지 않다면") {
            val invalidSrcs =
                listOf(
                    "data:image/png;base64,",
                    "data:image/png;base64",
                    "data:image/png;utf8,abc",
                    "data:image/png;BASE64,abc",
                    "data:image/png,abc",
                )

            invalidSrcs.forEachIndexed { index, invalidSrc ->
                it("업로드하지 않고 src를 유지한다 - 케이스 ${index + 1}") {
                    val doc = Jsoup.parse("<img src=\"$invalidSrc\" />")

                    imageTagConverter.base64ToUri(doc, postId)

                    verify(exactly = 1) { fileNamer.createUploadDirPath(FileUploadType.POST, postId) }
                    verify(exactly = 0) { FileManager.upload(any<ByteArrayInputStream>(), any<Path>()) }
                    verify(exactly = 0) { fileNamer.toUri(any()) }
                    doc.select("img").first()!!.attr("src") shouldBe invalidSrc
                }
            }
        }
    }
})
