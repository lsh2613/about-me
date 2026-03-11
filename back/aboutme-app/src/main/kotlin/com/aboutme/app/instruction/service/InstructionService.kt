package com.aboutme.app.instruction.service

import com.aboutme.app.instruction.port.`in`.InstructionUseCase
import com.aboutme.app.instruction.port.out.InstructionCommandPort
import com.aboutme.app.instruction.port.out.InstructionQueryPort
import com.aboutme.app.instruction.service.dto.command.InstructionCommand
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InstructionService(
    private val instructionQueryPort: InstructionQueryPort,
    private val instructionCommandPort: InstructionCommandPort,
) : InstructionUseCase {
    @Transactional
    override fun createOrUpdate(command: InstructionCommand) {
        instructionQueryPort.findOrNull()?.apply {
            update(
                name = command.name,
                emails = command.emails,
                region = command.region,
                education = command.education,
                skills = command.skills,
            )
            instructionCommandPort.update(this)
        } ?: let {
            instructionCommandPort.save(command.toDomain())
        }
    }

    @Transactional(readOnly = true)
    override fun readDetail(): InstructionDetailRep {
        val instruction = instructionQueryPort.findOrThrow()
        return InstructionDetailRep.from(instruction)
    }
}
