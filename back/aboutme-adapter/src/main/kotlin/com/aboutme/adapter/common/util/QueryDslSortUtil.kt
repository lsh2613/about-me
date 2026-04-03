package com.aboutme.adapter.common.util

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.PathBuilder
import org.springframework.data.domain.Sort

object QueryDslSortUtil {
    fun toOrderSpecifiers(
        sort: Sort,
        entityClass: Class<*>,
    ): Array<OrderSpecifier<*>> {
        val alias = entityClass.simpleName.replaceFirstChar { it.lowercase() }
        val pathBuilder = PathBuilder(entityClass, alias)

        return sort.toList().map { toOrderSpecifier(it, pathBuilder) }.toTypedArray()
    }

    private fun toOrderSpecifier(
        order: Sort.Order,
        pathBuilder: PathBuilder<*>,
    ): OrderSpecifier<*> {
        val direction = if (order.isAscending) Order.ASC else Order.DESC

        @Suppress("UNCHECKED_CAST")
        val path = pathBuilder.get(order.property) as Expression<Comparable<Any>>

        return OrderSpecifier(direction, path)
    }
}
