package com.aboutme.app.instruction.service.dto.command

import com.aboutme.core.instruction.domain.Instruction
import com.aboutme.core.instruction.vo.Email

data class InstructionCommand(
    val name: String,
    val emails: List<Email>,
    val region: String,
    val education: String,
    val skills: List<String>,
) {
    fun toDomain(): Instruction =
        Instruction(
            name = name,
            emails = emails,
            region = region,
            education = education,
            skills = skills,
        )
}
