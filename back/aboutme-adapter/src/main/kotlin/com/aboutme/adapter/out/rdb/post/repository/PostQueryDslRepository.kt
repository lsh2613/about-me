package com.aboutme.adapter.out.rdb.post.repository

import com.aboutme.adapter.common.enum.DeletionFilter
import com.aboutme.adapter.common.extension.orderBySort
import com.aboutme.adapter.common.vo.PagedResult
import com.aboutme.adapter.out.rdb.post.entity.PostEntity
import com.aboutme.adapter.out.rdb.post.entity.QPostEntity
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostQueryDslRepository(
    private val queryFactory: JPAQueryFactory,
) {
    val post = QPostEntity.postEntity

    fun findPagedResult(
        pageable: Pageable,
        deletionFilter: DeletionFilter,
    ): PagedResult<PostEntity> {
        val content =
            queryFactory
                .selectFrom(post)
                .where(condDeletionFilter(deletionFilter))
                .orderBySort(pageable.sort, PostEntity::class.java)
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        val countQuery =
            queryFactory
                .select(post)
                .from(post)
                .where(condDeletionFilter(deletionFilter))

        return PagedResult<PostEntity>(content, pageable, countQuery)
    }

    private fun condDeletionFilter(deletionFilter: DeletionFilter): BooleanExpression? =
        when (deletionFilter) {
            DeletionFilter.EXCLUDE_DELETED -> post.deletedAt.isNull
            DeletionFilter.INCLUDE_DELETED -> null
        }
}
