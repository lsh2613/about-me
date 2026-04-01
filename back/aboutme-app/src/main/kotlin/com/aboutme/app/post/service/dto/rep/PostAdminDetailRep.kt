package com.aboutme.app.post.service.dto.rep

import com.aboutme.core.post.domain.Post
import java.net.URL
import java.time.LocalDateTime

data class PostAdminDetailRep(
    val id: Long,
    val title: String,
    val content: String,
    val referenceUrls: List<URL>? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun from(post: Post) =
            PostAdminDetailRep(
                id = post.id!!,
                title = post.title!!,
                content = post.content!!,
                referenceUrls = post.referenceUrls,
                createdAt = post.createdAt!!,
                updatedAt = post.updatedAt!!,
                deletedAt = post.deletedAt,
            )
    }
}
