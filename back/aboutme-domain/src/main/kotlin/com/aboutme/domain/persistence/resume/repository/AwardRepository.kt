package com.aboutme.domain.persistence.resume.repository

import com.aboutme.domain.persistence.resume.entity.Award
import org.springframework.data.jpa.repository.JpaRepository

interface AwardRepository : JpaRepository<Award, Long>
