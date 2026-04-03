package com.aboutme.app.post.service

import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.file.service.util.FileManager
import com.aboutme.app.post.port.`in`.PostUseCase
import com.aboutme.app.post.port.out.PostCommandPort
import com.aboutme.app.post.port.out.PostQueryPort
import com.aboutme.app.post.service.dto.PostCreateOrUpdateCommand
import com.aboutme.app.post.service.dto.rep.PostAdminDetailRep
import com.aboutme.app.post.service.dto.rep.PostDetailRep
import com.aboutme.app.post.service.helper.ImageTagConverter
import com.aboutme.core.file.domain.FileUploadType
import com.aboutme.core.post.domain.Post
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postQueryPort: PostQueryPort,
    private val postCommandPort: PostCommandPort,
    private val imageTagConverter: ImageTagConverter,
    private val fileNamer: FileNamer,
) : PostUseCase {
    @Transactional
    override fun create(command: PostCreateOrUpdateCommand) {
        val post = saveDefaultPost()

        val doc = Jsoup.parse(command.content)

        imageTagConverter.base64ToUri(doc, post.id!!)

        updatePost(post, command, doc)
    }

    private fun saveDefaultPost() = postCommandPort.save(Post())

    @Transactional(readOnly = true)
    override fun readDetails(pageable: Pageable): Page<PostDetailRep> {
        return postQueryPort.findDetailsPage(pageable).map(PostDetailRep::from)
    }

    @Transactional(readOnly = true)
    override fun readAdminDetails(pageable: Pageable): Page<PostAdminDetailRep> {
        return postQueryPort.findAdminDetailsPage(pageable).map(PostAdminDetailRep::from)
    }

    private fun updatePost(
        post: Post,
        command: PostCreateOrUpdateCommand,
        doc: Document,
    ) {
        post.update(
            command.title,
            doc.toString(),
            command.referenceUrls,
        )
        postCommandPort.update(post)
    }

    @Transactional
    override fun update(
        command: PostCreateOrUpdateCommand,
        postId: Long,
    ) {
        val post = postQueryPort.findOrThrow(postId)

        val doc = Jsoup.parse(command.content)
        imageTagConverter.base64ToUri(doc, post.id!!)

        updatePost(post, command, doc)
    }

    @Transactional
    override fun restore(postId: Long) = postCommandPort.restore(postId)

    @Transactional
    override fun softDelete(postId: Long) = postCommandPort.softDelete(postId)

    @Transactional
    override fun hardDelete(postId: Long) {
        postCommandPort.hardDelete(postId)

        val postDirPath = fileNamer.createUploadDirPath(FileUploadType.POST, postId)
        FileManager.deleteIfExists(postDirPath)
    }
}
