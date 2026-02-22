package com.aboutme.domain.persistence.resume.repository

import com.aboutme.domain.persistence.resume.entity.Activity
import org.springframework.data.jpa.repository.JpaRepository

interface ActivityRepository : JpaRepository<Activity, Long>
