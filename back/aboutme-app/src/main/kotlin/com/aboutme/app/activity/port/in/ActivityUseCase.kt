package com.aboutme.app.activity.port.`in`

import com.aboutme.app.activity.service.dto.command.ActivitySyncCommand
import com.aboutme.app.activity.service.dto.rep.ActivityDetailRep

interface ActivityUseCase {
    fun sync(commands: List<ActivitySyncCommand>)

    fun deleteAll()

    fun readDetails(): List<ActivityDetailRep>
}
