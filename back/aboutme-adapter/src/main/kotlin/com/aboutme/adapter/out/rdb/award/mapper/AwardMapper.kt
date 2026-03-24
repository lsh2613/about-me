package com.aboutme.adapter.out.rdb.award.mapper

import com.aboutme.adapter.out.rdb.award.entity.AwardEntity
import com.aboutme.core.award.domain.Award

class AwardMapper {
    companion object {
        fun toDomain(entity: AwardEntity) =
            Award(
                id = entity.id,
                name = entity.name,
                issuer = entity.issuer,
                issueDate = entity.issueDate,
                description = entity.description,
                seq = entity.seq,
            )

        fun toEntity(activity: Award) =
            AwardEntity(
                name = activity.name,
                issuer = activity.issuer,
                issueDate = activity.issueDate,
                description = activity.description,
                seq = activity.seq,
            )
    }
}
