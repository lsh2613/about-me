package com.aboutme.app.post.port.out

import com.aboutme.core.post.domain.Post

interface PostCommandPort {
    fun save(post: Post): Post

    fun update(post: Post)

    fun restore(postId: Long)

    fun softDelete(postId: Long)

    fun hardDelete(postId: Long)
}
