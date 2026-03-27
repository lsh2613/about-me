package com.aboutme.app.essay.port.`in`

import com.aboutme.app.essay.service.dto.command.EssaySyncCommand
import com.aboutme.app.essay.service.dto.rep.EssayDetailRep

interface EssayUseCase {
    fun sync(commands: List<EssaySyncCommand>)

    fun readAll(): List<EssayDetailRep>

    fun deleteAll()
}
