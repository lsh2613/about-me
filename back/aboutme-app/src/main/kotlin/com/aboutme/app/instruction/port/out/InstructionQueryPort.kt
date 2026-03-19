package com.aboutme.app.instruction.port.out

import com.aboutme.core.instruction.domain.Instruction

interface InstructionQueryPort {
    fun findOrNull(): Instruction?

    fun findOrThrow(): Instruction
}
