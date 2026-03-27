package com.aboutme.adapter.out.rdb.essay.mapper

import com.aboutme.adapter.out.rdb.essay.entity.EssayEntity
import com.aboutme.core.essay.domain.Essay

class EssayMapper {
    companion object {
        fun toDomain(entity: EssayEntity) =
            Essay(
                id = entity.id,
                title = entity.title,
                content = entity.content,
                seq = entity.seq,
            )

        fun toEntity(essay: Essay) =
            EssayEntity(
                title = essay.title,
                content = essay.content,
                seq = essay.seq,
            )
    }
}
