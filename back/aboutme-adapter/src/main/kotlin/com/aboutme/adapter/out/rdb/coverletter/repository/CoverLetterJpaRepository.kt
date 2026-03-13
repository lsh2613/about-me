package com.aboutme.adapter.out.rdb.coverletter.repository

import com.aboutme.adapter.out.rdb.coverletter.entity.CoverLetterEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CoverLetterJpaRepository : JpaRepository<CoverLetterEntity, Long>
