package com.aboutme.app.post.service.dto.rep

import com.aboutme.core.post.domain.Post
import java.net.URL
import java.time.LocalDateTime

data class PostDetailRep(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val referenceUrls: List<URL>? = null,
) {
    companion object {
        fun from(post: Post) =
            PostDetailRep(
                id = post.id!!,
                title = post.title!!,
                content = post.content!!,
                referenceUrls = post.referenceUrls,
                createdAt = post.createdAt!!,
            )
    }
}
