package com.aboutme.domain.persistence.resume.repository

import com.aboutme.domain.persistence.resume.entity.Instruction
import org.springframework.data.jpa.repository.JpaRepository

interface InstructionRepository : JpaRepository<Instruction, Long>
