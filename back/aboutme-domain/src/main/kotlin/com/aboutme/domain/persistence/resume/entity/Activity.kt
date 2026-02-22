package com.aboutme.domain.persistence.resume.entity

import com.aboutme.domain.persistence.common.entity.BaseEntity
import com.aboutme.domain.persistence.resume.entity.enum.ActivityType
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "activity")
@Entity
class Activity(
    name: String,
    activityType: ActivityType,
    startedAt: LocalDateTime,
    endedAt: LocalDateTime,
    description: String,
) : BaseEntity() {
    var name: String = name
        protected set
    var activityType: ActivityType = activityType
        protected set
    var startedAt: LocalDateTime = startedAt
        protected set
    var endedAt: LocalDateTime = endedAt
        protected set
    var description: String? = description
        protected set
}
