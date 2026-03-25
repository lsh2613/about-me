package com.aboutme.adapter.out.rdb.activity

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.activity.entity.ActivityEntity
import com.aboutme.adapter.out.rdb.activity.mapper.ActivityMapper
import com.aboutme.adapter.out.rdb.activity.repository.ActivityJpaRepository
import com.aboutme.app.activity.port.out.ActivityCommandPort
import com.aboutme.app.activity.port.out.ActivityQueryPort
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.activity.domain.Activity
import com.aboutme.core.activity.error.ActivityErrorCode

@Adapter
class ActivityAdapter(
    private val activityJpaRepository: ActivityJpaRepository,
) : ActivityCommandPort, ActivityQueryPort {
    override fun save(activity: Activity) {
        activityJpaRepository.save(ActivityMapper.toEntity(activity))
    }

    override fun findAll(): List<Activity> {
        return activityJpaRepository.findAll().map(ActivityMapper::toDomain)
    }

    override fun findOrThrow(id: Long): Activity {
        return findEntityByIdOrThrow(id).let(ActivityMapper::toDomain)
    }

    override fun delete(ids: List<Long>) {
        activityJpaRepository.deleteAllById(ids)
    }

    override fun update(activity: Activity) {
        findEntityByIdOrThrow(activity.id!!).apply {
            update(
                name = activity.name,
                activityType = activity.activityType,
                startDate = activity.dateRange.startDate,
                endDate = activity.dateRange.endDate,
                description = activity.description,
                seq = activity.seq,
            )
        }
    }

    override fun deleteAll() {
        activityJpaRepository.deleteAll()
    }

    private fun findEntityByIdOrThrow(id: Long): ActivityEntity {
        return activityJpaRepository.findById(id).orElseThrow {
            throw GlobalException(ActivityErrorCode.NOT_FOUND)
        }
    }
}
