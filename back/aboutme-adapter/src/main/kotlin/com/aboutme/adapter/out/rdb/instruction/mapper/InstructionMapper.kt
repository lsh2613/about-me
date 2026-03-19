package com.aboutme.adapter.out.rdb.instruction.mapper

import com.aboutme.adapter.out.rdb.instruction.entity.InstructionEntity
import com.aboutme.core.instruction.domain.Instruction
import com.aboutme.core.instruction.vo.Email

class InstructionMapper {
    companion object {
        fun toDomain(entity: InstructionEntity): Instruction =
            Instruction(
                name = entity.name,
                emails = entity.emails.map { Email(it) },
                region = entity.region,
                education = entity.education,
                skills = entity.skills,
                profileImagePath = entity.profileImagePath,
            )

        fun toEntity(instruction: Instruction): InstructionEntity =
            InstructionEntity(
                name = instruction.name,
                emails = instruction.emails.map { it.value },
                region = instruction.region,
                education = instruction.education,
                skills = instruction.skills,
                profileImagePath = instruction.profileImagePath,
            )
    }
}
