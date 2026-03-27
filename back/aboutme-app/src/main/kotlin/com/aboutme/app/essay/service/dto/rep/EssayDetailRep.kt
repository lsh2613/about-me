package com.aboutme.app.essay.service.dto.rep

import com.aboutme.core.essay.domain.Essay

data class EssayDetailRep(
    val id: Long?,
    val title: String,
    val content: String,
    val seq: Int,
) {
    companion object {
        fun of(essay: Essay) =
            EssayDetailRep(
                id = essay.id,
                title = essay.title,
                content = essay.content,
                seq = essay.seq,
            )
    }
}
