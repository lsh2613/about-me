package com.aboutme.adapter.common.resolver.page

import org.springframework.core.MethodParameter
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CustomPageableArgumentResolver : HandlerMethodArgumentResolver {
    companion object {
        private const val DEFAULT_PAGE_SIZE = 12
        private val DEFAULT_SORT_DIRECTION = Sort.Direction.DESC
        private val DEFAULT_SORT = Sort.by(DEFAULT_SORT_DIRECTION, "createdAt")
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return Pageable::class.java == parameter.getParameterType()
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        var page = getPage(webRequest)
        val size = getSize(webRequest)
        val sort = getSort(webRequest)

        if (page < 0) {
            page = 0
        }
        return CustomPageRequest(page, size, sort)
    }

    private fun getPage(webRequest: NativeWebRequest): Int {
        val page = webRequest.getParameter("page")
        return page?.toInt()?.minus(1) ?: 0
    }

    private fun getSize(webRequest: NativeWebRequest): Int {
        val size = webRequest.getParameter("size")
        return size?.toInt() ?: DEFAULT_PAGE_SIZE
    }

    private fun getSort(webRequest: NativeWebRequest): Sort {
        val paramValues = webRequest.getParameterValues("sort")

        if (paramValues.isNullOrEmpty()) {
            return DEFAULT_SORT
        }

        val orders: MutableList<Sort.Order?> = ArrayList()
        for (paramValue in paramValues) {
            if (paramValue.isNullOrBlank()) continue

            val sortInfo = paramValue.split(",")
            require(sortInfo.size <= 2) { "sort 파라미터의 값은 '{field},{direction}' 형식으로 2개까지만 허용됩니다. (예: sort=createdAt,DESC)" }

            val sortOrder = sortOrder(sortInfo)
            orders.add(sortOrder)
        }

        return Sort.by(orders)
    }

    private fun sortOrder(sortInfo: List<String>): Sort.Order {
        val field = sortInfo[0]
        val direction = sortInfo.getOrNull(1)
        val sortDirection = direction?.let { Sort.Direction.fromString(it) } ?: DEFAULT_SORT_DIRECTION
        return Sort.Order(sortDirection, field)
    }
}
