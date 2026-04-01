package com.aboutme.app.post.port.out

import com.aboutme.core.post.domain.Post

interface PostQueryPort {
    fun findOrThrow(postId: Long): Post

    fun findAll(): List<Post>

    fun findAllWithDeleted(): List<Post>
}
