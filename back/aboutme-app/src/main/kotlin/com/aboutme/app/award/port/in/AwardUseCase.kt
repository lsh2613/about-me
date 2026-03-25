package com.aboutme.app.award.port.`in`

import com.aboutme.app.award.service.dto.command.AwardSyncCommand
import com.aboutme.app.award.service.dto.rep.AwardDetailRep

interface AwardUseCase {
    fun sync(commands: List<AwardSyncCommand>)

    fun readDetails(): List<AwardDetailRep>

    fun deleteAll()
}
