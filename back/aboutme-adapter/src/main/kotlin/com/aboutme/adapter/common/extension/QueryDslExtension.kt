package com.aboutme.adapter.common.extension

import com.aboutme.adapter.common.util.QueryDslSortUtil
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Sort

fun <T> JPQLQuery<T>.orderBySort(
    sort: Sort,
    entityClass: Class<*>,
): JPQLQuery<T> {
    return this.orderBy(*QueryDslSortUtil.toOrderSpecifiers(sort, entityClass))
}
