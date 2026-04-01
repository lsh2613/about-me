package com.aboutme.adapter.out.rdb.post.mapper

import com.aboutme.adapter.out.rdb.post.entity.PostEntity
import com.aboutme.core.post.domain.Post
import java.net.URI

class PostMapper {
    companion object {
        fun toEntity(post: Post): PostEntity {
            return PostEntity(
                id = post.id,
                title = post.title,
                content = post.content,
                references = post.referenceUrls?.map { it.toString() } ?: emptyList(),
            )
        }

        fun toDomain(postEntity: PostEntity): Post {
            return Post(
                id = postEntity.id,
                title = postEntity.title,
                content = postEntity.content,
                referenceUrls = postEntity.referenceUrls?.map { URI(it).toURL() },
                createdAt = postEntity.createdAt,
                updatedAt = postEntity.updatedAt,
                deletedAt = postEntity.deletedAt,
            )
        }
    }
}
