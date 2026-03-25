package com.aboutme.app.activity.service.dto.rep

import com.aboutme.core.activity.domain.Activity
import java.time.LocalDate
import java.time.LocalDateTime

data class ActivityDetailRep(
    val id: Long,
    val name: String,
    val activityTypeDesc: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val description: String,
    val seq: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
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
