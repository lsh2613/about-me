package com.aboutme.core.activity.domain

import java.time.LocalDate
import java.time.LocalDateTime

data class Activity(
    var id: Long? = null,
    var name: String,
    var activityType: ActivityType,
    var startDate: LocalDate,
    var endDate: LocalDate?,
    var description: String,
    var seq: Int,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
) {
    fun update(
        name: String,
        activityType: ActivityType,
        startDate: LocalDate,
        endDate: LocalDate?,
        description: String,
        seq: Int,
    ) {
        this.name = name
        this.activityType = activityType
        this.startDate = startDate
        this.endDate = endDate
        this.description = description
        this.seq = seq
    }
}
