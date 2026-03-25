package com.aboutme.app.activity.service

import com.aboutme.app.activity.port.`in`.ActivityUseCase
import com.aboutme.app.activity.port.out.ActivityCommandPort
import com.aboutme.app.activity.port.out.ActivityQueryPort
import com.aboutme.app.activity.service.dto.command.ActivitySyncCommand
import com.aboutme.app.activity.service.dto.rep.ActivityDetailRep
import com.aboutme.app.common.util.CommonValidationUtil
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.activity.error.ActivityErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ActivityService(
    private val activityQueryPort: ActivityQueryPort,
    private val activityCommandPort: ActivityCommandPort,
) : ActivityUseCase {
    @Transactional
    override fun sync(commands: List<ActivitySyncCommand>) {
        CommonValidationUtil.validateSequence(commands.map { it.seq })
        validateChronologicalPeriod(commands)

        deleteNotInCommands(commands)
        saveOrUpdate(commands)
    }

    @Transactional(readOnly = true)
    override fun readDetails(): List<ActivityDetailRep> {
        return activityQueryPort.findAll().map { ActivityDetailRep.of(it) }
    }

    @Transactional
    override fun deleteAll() {
        activityCommandPort.deleteAll()
    }

    private fun deleteNotInCommands(commands: List<ActivitySyncCommand>) {
        val activities = activityQueryPort.findAll()
        val deleteIds = activities.mapNotNull { it.id } - commands.mapNotNull { it.activityId }.toSet()
        activityCommandPort.delete(deleteIds)
    }

    private fun saveOrUpdate(commands: List<ActivitySyncCommand>) {
        commands.forEach { command ->
            command.activityId?.let {
                update(it, command)
            } ?: save(command)
        }
    }

    private fun save(command: ActivitySyncCommand) {
        activityCommandPort.save(command.toDomain())
    }

    private fun update(
        id: Long,
        command: ActivitySyncCommand,
    ) {
        activityQueryPort.findOrThrow(id).apply {
            update(
                name = command.name,
                activityType = command.activityType,
                startDate = command.startDate,
                endDate = command.endDate,
                description = command.description,
                seq = command.seq,
            )
            activityCommandPort.update(this)
        }
    }

    private fun validateChronologicalPeriod(commands: List<ActivitySyncCommand>) {
        commands.forEach { command ->
            command.endDate?.let { endDate ->
                if (endDate.isBefore(command.startDate)) {
                    throw GlobalException(ActivityErrorCode.INVALID_PERIOD)
                }
            }
        }
    }
}
