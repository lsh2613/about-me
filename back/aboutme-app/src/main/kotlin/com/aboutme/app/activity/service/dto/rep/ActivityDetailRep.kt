package com.aboutme.app.activity.service.dto.rep

import com.aboutme.core.activity.domain.Activity
import java.time.LocalDate
import java.time.LocalDateTime

data class ActivityDetailRep(
    var id: Long,
    var name: String,
    var activityTypeDesc: String,
    var startDate: LocalDate,
    var endDate: LocalDate?,
    var description: String,
    var seq: Int,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) {
    companion object {
        fun of(activity: Activity): ActivityDetailRep {
            return ActivityDetailRep(
                id = activity.id!!,
                name = activity.name,
                activityTypeDesc = activity.activityType.desc,
                startDate = activity.startDate,
                endDate = activity.endDate,
                description = activity.description,
                seq = activity.seq,
                createdAt = activity.createdAt!!,
                updatedAt = activity.updatedAt!!,
            )
        }
    }
}
