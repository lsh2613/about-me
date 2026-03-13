package com.aboutme.adapter.out.rdb.instruction

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.instruction.entity.InstructionEntity
import com.aboutme.adapter.out.rdb.instruction.mapper.InstructionMapper
import com.aboutme.adapter.out.rdb.instruction.repository.InstructionJpaRepository
import com.aboutme.app.instruction.port.out.InstructionCommandPort
import com.aboutme.app.instruction.port.out.InstructionQueryPort
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.instruction.domain.Instruction
import com.aboutme.core.instruction.error.InstructionErrorCode
import org.springframework.transaction.annotation.Transactional

@Adapter
class InstructionAdapter(
    private val instructionJpaRepository: InstructionJpaRepository,
) : InstructionCommandPort, InstructionQueryPort {
    @Transactional
    override fun save(instruction: Instruction) {
        instructionJpaRepository.save(InstructionMapper.toEntity(instruction))
    }

    @Transactional(readOnly = true)
    override fun findOrNull(): Instruction? {
        return instructionJpaRepository.findById(InstructionEntity.SINGLETON_ID).orElse(null)
            ?.let(InstructionMapper::toDomain)
    }

    @Transactional(readOnly = true)
    override fun findOrThrow(): Instruction {
        return findEntityByIdOrThrow().let(InstructionMapper::toDomain)
    }

    @Transactional
    override fun update(instruction: Instruction) {
        findEntityByIdOrThrow().apply {
            update(
                name = instruction.name,
                emails = instruction.emails.map { it.value },
                region = instruction.region,
                education = instruction.education,
                skills = instruction.skills,
                profileImageUrl = instruction.profileImageUrl,
            )
        }
    }

    @Transactional
    override fun updateProfile(path: String) {
        findEntityByIdOrThrow().apply {
            updateProfile(path)
        }
    }

    @Transactional
    override fun deleteProfile() {
        findEntityByIdOrThrow().apply {
            updateProfile()
        }
    }

    private fun findEntityByIdOrThrow(): InstructionEntity =
        instructionJpaRepository.findById(InstructionEntity.SINGLETON_ID)
            .orElseThrow { throw GlobalException(InstructionErrorCode.NOT_FOUND) }
}
