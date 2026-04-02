package com.aboutme.adapter.common.resolver.page

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class CustomPageRequest(
    pageNumber: Int,
    pageSize: Int,
    sort: Sort,
) : PageRequest(pageNumber, pageSize, sort) {
    override fun getPageNumber() = super.getPageNumber() + 1
}
