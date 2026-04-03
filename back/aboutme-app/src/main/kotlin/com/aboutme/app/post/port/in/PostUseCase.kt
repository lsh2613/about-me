package com.aboutme.app.post.port.`in`

import com.aboutme.app.post.service.dto.PostCreateOrUpdateCommand
import com.aboutme.app.post.service.dto.rep.PostAdminDetailRep
import com.aboutme.app.post.service.dto.rep.PostDetailRep
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostUseCase {
    fun create(command: PostCreateOrUpdateCommand)

    fun readDetails(pageable: Pageable): Page<PostDetailRep>

    fun readAdminDetails(pageable: Pageable): Page<PostAdminDetailRep>

    fun update(
        command: PostCreateOrUpdateCommand,
        postId: Long,
    )

    fun restore(postId: Long)

    fun softDelete(postId: Long)

    fun hardDelete(postId: Long)
}
