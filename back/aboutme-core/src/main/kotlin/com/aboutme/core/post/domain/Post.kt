package com.aboutme.core.post.domain

import com.aboutme.core.common.domain.SoftDeletableDomain
import java.net.URL
import java.time.LocalDateTime

data class Post(
    var id: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var referenceUrls: List<URL>? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedAt: LocalDateTime? = null,
    override var deletedAt: LocalDateTime? = null,
) : SoftDeletableDomain(createdAt, updatedAt, deletedAt) {
    fun update(
        title: String,
        content: String,
        referenceUrls: List<URL>? = null,
    ) {
        this.title = title
        this.content = content
        this.referenceUrls = referenceUrls
    }

    fun restore() {
        this.deletedAt = null
    }
}
