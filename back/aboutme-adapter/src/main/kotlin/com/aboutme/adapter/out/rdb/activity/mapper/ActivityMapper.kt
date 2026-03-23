package com.aboutme.adapter.out.rdb.activity.mapper

import com.aboutme.adapter.out.rdb.activity.entity.ActivityEntity
import com.aboutme.core.activity.domain.Activity

class ActivityMapper {
    companion object {
        fun toDomain(entity: ActivityEntity) =
            Activity(
                id = entity.id,
                name = entity.name,
                activityType = entity.activityType,
                startDate = entity.startDate,
                endDate = entity.endDate,
                description = entity.description,
                seq = entity.seq,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
            )

        fun toEntity(activity: Activity) =
            ActivityEntity(
                name = activity.name,
                activityType = activity.activityType,
                startDate = activity.startDate,
                endDate = activity.endDate,
                description = activity.description,
                seq = activity.seq,
            )
    }
}
