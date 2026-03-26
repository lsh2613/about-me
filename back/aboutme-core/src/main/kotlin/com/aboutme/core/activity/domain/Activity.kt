package com.aboutme.core.activity.domain

import com.aboutme.core.common.vo.DateRange
import java.time.LocalDateTime

data class Activity(
    var id: Long? = null,
    var name: String,
    var activityType: ActivityType,
    var dateRange: DateRange,
    var description: String,
    var seq: Int,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
) {
    fun update(
        name: String,
        activityType: ActivityType,
        dateRange: DateRange,
        description: String,
        seq: Int,
    ) {
        this.name = name
        this.activityType = activityType
        this.dateRange = dateRange
        this.description = description
        this.seq = seq
    }
}
