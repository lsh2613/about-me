package com.aboutme.app.post.service

import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.post.port.out.PostCommandPort
import com.aboutme.app.post.port.out.PostQueryPort
import com.aboutme.app.post.service.dto.PostCreateOrUpdateCommand
import com.aboutme.app.post.service.helper.ImageTagConverter
import com.aboutme.core.post.domain.Post
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URI
import java.net.URL

@ExtendWith(MockKExtension::class)
class PostServiceTest : DescribeSpec({
    val postQueryPort = mockk<PostQueryPort>(relaxed = true)
    val postCommandPort = mockk<PostCommandPort>(relaxed = true)
    val imageTagConverter = mockk<ImageTagConverter>(relaxed = true)
    val fileNamer = mockk<FileNamer>(relaxed = true)

    val postService = PostService(postQueryPort, postCommandPort, imageTagConverter, fileNamer)

    fun createCommand(
        title: String = "게시글 제목",
        content: String = "<p>본문</p>",
        referenceUrls: List<URL>? = listOf(URI("https://aboutme.com").toURL()),
    ) = PostCreateOrUpdateCommand(
        title = title,
        content = content,
        referenceUrls = referenceUrls,
    )

    describe("게시글 생성") {
        val command = createCommand(content = "<p>신규 본문</p><img src=\"data:image/png;base64,AAA\"/>")
        val post = Post(id = 1L)
        every { postCommandPort.save(any()) } returns post

        postService.create(command)

        it("기본 게시글을 저장한 뒤 이미지 태그를 변환하고 게시글을 업데이트한다") {
            verify(exactly = 1) { postCommandPort.save(Post()) }
            verify(exactly = 1) { imageTagConverter.base64ToUri(any(), 1L) }
            verify(exactly = 1) { postCommandPort.update(post) }
        }
    }

    describe("게시글 수정") {
        val command =
            createCommand(
                title = "수정 제목",
                content = "<div>수정 본문</div><img src=\"data:image/jpeg;base64,BBB\"/>",
                referenceUrls = listOf(URI("https://aboutme.com/post").toURL()),
            )
        val post = Post(id = 1L, title = "기존 제목", content = "기존 본문")

        every { postQueryPort.findOrThrow(post.id!!) } returns post

        postService.update(command, post.id!!)
        it("대상 게시글을 조회한 뒤 이미지 태그를 변환하고 게시글을 업데이트한다") {
            verify(exactly = 1) { postQueryPort.findOrThrow(post.id!!) }
            verify(exactly = 1) { imageTagConverter.base64ToUri(any(), post.id!!) }
            verify(exactly = 1) { postCommandPort.update(post) }
        }
    }
})
