package com.aboutme.app.instruction.port.`in`

import com.aboutme.app.common.annotation.UseCase
import com.aboutme.app.instruction.service.dto.command.InstructionCommand
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep

@UseCase
interface InstructionUseCase {
    fun createOrUpdate(command: InstructionCommand)

    fun readDetail(): InstructionDetailRep
}
