package com.aboutme.app.instruction.service.dto.rep

import com.aboutme.core.instruction.domain.Instruction

data class InstructionDetailRep(
    val name: String,
    val emails: List<String>,
    val region: String,
    val education: String,
    val skills: List<String>,
    val profileImageUrl: String?,
) {
    companion object {
        fun from(instruction: Instruction): InstructionDetailRep {
            return InstructionDetailRep(
                name = instruction.name,
                emails = instruction.emails.map { it.value },
                region = instruction.region,
                education = instruction.education,
                skills = instruction.skills,
                profileImageUrl = instruction.profileImagePath,
            )
        }
    }
}
