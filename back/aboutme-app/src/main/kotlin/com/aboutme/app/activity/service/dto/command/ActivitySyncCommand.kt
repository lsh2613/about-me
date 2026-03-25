package com.aboutme.app.activity.service.dto.command

import com.aboutme.core.activity.domain.Activity
import com.aboutme.core.activity.domain.ActivityType
import com.aboutme.core.common.vo.DateRange

data class ActivitySyncCommand(
    val activityId: Long? = null,
    val name: String,
    val activityType: ActivityType,
    val dateRange: DateRange,
    val description: String,
    val seq: Int,
) {
    fun toDomain(): Activity {
        return Activity(
            id = this.activityId,
            name = this.name,
            activityType = this.activityType,
            dateRange = this.dateRange,
            description = this.description,
            seq = this.seq,
        )
    }
}
