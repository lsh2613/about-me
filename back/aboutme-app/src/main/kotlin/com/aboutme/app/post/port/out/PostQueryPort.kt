package com.aboutme.app.post.port.out

import com.aboutme.core.post.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostQueryPort {
    fun findOrThrow(postId: Long): Post

    fun findAll(pageable: Pageable): Page<Post>

    fun findAdminDetailsPage(pageable: Pageable): Page<Post>
}
