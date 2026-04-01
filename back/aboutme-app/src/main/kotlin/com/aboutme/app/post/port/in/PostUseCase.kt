package com.aboutme.app.post.port.`in`

import com.aboutme.app.post.service.dto.PostCreateOrUpdateCommand
import com.aboutme.app.post.service.dto.rep.PostAdminDetailRep
import com.aboutme.app.post.service.dto.rep.PostDetailRep

interface PostUseCase {
    fun create(command: PostCreateOrUpdateCommand)

    fun readDetails(): List<PostDetailRep>

    fun readAdminDetails(): List<PostAdminDetailRep>

    fun update(
        command: PostCreateOrUpdateCommand,
        postId: Long,
    )

    fun restore(postId: Long)

    fun softDelete(postId: Long)

    fun hardDelete(postId: Long)
}
