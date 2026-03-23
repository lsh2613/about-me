package com.aboutme.adapter.out.rdb.activity.entity

import com.aboutme.adapter.out.rdb.common.entity.BaseEntity
import com.aboutme.core.activity.domain.ActivityType
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Table(name = "activity")
@Entity
class ActivityEntity(
    name: String,
    activityType: ActivityType,
    startDate: LocalDate,
    endDate: LocalDate?,
    description: String,
    seq: Int,
) : BaseEntity() {
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

    var name: String = name
        protected set
    var activityType: ActivityType = activityType
        protected set
    var startDate: LocalDate = startDate
        protected set
    var endDate: LocalDate? = endDate
        protected set
    var description: String = description
        protected set
    var seq: Int = seq
}
