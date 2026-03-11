package com.aboutme.adapter.out.rdb.instruction.repository

import com.aboutme.adapter.out.rdb.instruction.entity.InstructionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InstructionJpaRepository : JpaRepository<InstructionEntity, Long>
