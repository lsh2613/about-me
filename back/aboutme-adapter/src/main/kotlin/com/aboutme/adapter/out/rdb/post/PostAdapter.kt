package com.aboutme.adapter.out.rdb.post

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.post.entity.PostEntity
import com.aboutme.adapter.out.rdb.post.mapper.PostMapper
import com.aboutme.adapter.out.rdb.post.repository.PostJpaRepository
import com.aboutme.adapter.out.rdb.post.repository.PostQueryDslRepository
import com.aboutme.app.post.port.out.PostCommandPort
import com.aboutme.app.post.port.out.PostQueryPort
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.post.domain.Post
import com.aboutme.core.post.domain.error.PostErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Adapter
class PostAdapter(
    private val postJpaRepository: PostJpaRepository,
    private val postQueryDslRepository: PostQueryDslRepository,
) : PostQueryPort, PostCommandPort {
    override fun save(post: Post): Post {
        val postEntity = postJpaRepository.save(PostMapper.toEntity(post))
        return PostMapper.toDomain(postEntity)
    }

    override fun findOrThrow(postId: Long): Post {
        return findEntityByIdOrThrow(postId).let(PostMapper::toDomain)
    }

    override fun findAll(pageable: Pageable): Page<Post> {
        val result = postQueryDslRepository.findPagedResult(pageable, false)
        return result.getPage(PostMapper::toDomain)
    }

    override fun findAdminDetailsPage(pageable: Pageable): Page<Post> {
        val result = postQueryDslRepository.findPagedResult(pageable, true)
        return result.getPage(PostMapper::toDomain)
    }

    override fun update(post: Post) {
        findEntityByIdOrThrow(post.id!!).apply {
            update(
                title = post.title,
                content = post.content,
                references = post.referenceUrls?.map { it.toString() },
            )
        }
    }

    override fun restore(postId: Long) {
        postJpaRepository.restore(postId)
    }

    override fun softDelete(postId: Long) = postJpaRepository.deleteById(postId)

    override fun hardDelete(postId: Long) = postJpaRepository.hardDelete(postId)

    private fun findEntityByIdOrThrow(id: Long): PostEntity {
        return postJpaRepository.findById(id).orElseThrow { GlobalException(PostErrorCode.NOT_FOUND) }
    }
}
