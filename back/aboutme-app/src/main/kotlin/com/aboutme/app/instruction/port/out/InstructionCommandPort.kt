package com.aboutme.app.instruction.port.out

import com.aboutme.core.instruction.domain.Instruction

interface InstructionCommandPort {
    fun save(instruction: Instruction)

    fun update(instruction: Instruction)
}
