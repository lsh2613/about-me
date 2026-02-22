package com.aboutme.domain.persistence.resume.repository

import com.aboutme.domain.persistence.resume.entity.CoverLetter
import org.springframework.data.jpa.repository.JpaRepository

interface CoverLetterRepository : JpaRepository<CoverLetter, Long>
