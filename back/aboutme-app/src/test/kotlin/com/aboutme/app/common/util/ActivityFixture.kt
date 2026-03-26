package com.aboutme.app.common.util

import com.aboutme.common.annotation.Util
import com.aboutme.core.activity.domain.Activity
import com.aboutme.core.activity.domain.ActivityType
import com.aboutme.core.common.vo.DateRange
import com.appmattus.kotlinfixture.Fixture
import java.time.LocalDate
import java.time.LocalDateTime

@Util
class ActivityFixture {
    companion object {
        fun create(
            id: Long? = 1L,
            name: String = "테스터",
            activityType: ActivityType = ActivityType.PART_TIME,
            dateRange: DateRange = DateRange(LocalDate.now(), LocalDate.now()),
            description: String = "설명",
            seq: Int = 1,
            createdAt: LocalDateTime? = LocalDateTime.now(),
            updatedAt: LocalDateTime? = LocalDateTime.now(),
        ): Activity {
            val fixture = Fixture()
            return fixture<Activity> {
                property(Activity::id) { id }
                property(Activity::name) { name }
                property(Activity::activityType) { activityType }
                property(Activity::dateRange) { dateRange }
                property(Activity::description) { description }
                property(Activity::seq) { seq }
                property(Activity::createdAt) { createdAt }
                property(Activity::updatedAt) { updatedAt }
            }
        }
    }
}
