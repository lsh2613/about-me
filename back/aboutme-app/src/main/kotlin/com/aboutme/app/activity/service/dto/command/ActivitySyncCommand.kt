package com.aboutme.app.activity.service.dto.command

import com.aboutme.core.activity.domain.Activity
import com.aboutme.core.activity.domain.ActivityType
import java.time.LocalDate

data class ActivitySyncCommand(
    val activityId: Long? = null,
    val name: String,
    val activityType: ActivityType,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val description: String,
    val seq: Int,
) {
    fun toDomain(): Activity {
        return Activity(
            id = activityId,
            name = this.name,
            activityType = this.activityType,
            startDate = this.startDate,
            endDate = this.endDate,
            description = this.description,
            seq = this.seq,
        )
    }
}
