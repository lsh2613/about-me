package com.aboutme.core.essay.domain

data class Essay(
    var id: Long? = null,
    var title: String,
    var content: String,
    var seq: Int,
) {
    fun update(
        title: String,
        content: String,
        seq: Int,
    ) {
        this.title = title
        this.content = content
        this.seq = seq
    }
}
