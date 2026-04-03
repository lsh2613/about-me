package com.aboutme.adapter.common.vo

import com.aboutme.adapter.out.rdb.common.entity.BaseEntity
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

data class PagedResult<T : BaseEntity>(
    val content: List<T>,
    val pageable: Pageable,
    val countQuery: JPQLQuery<T>,
) {
    fun <R> getPage(mapper: (T) -> R): Page<R> {
        return PageableExecutionUtils.getPage(
            content.map(mapper),
            pageable,
            countQuery::fetchCount,
        )
    }
}
