package com.aboutme.domain.persistence.project.repository

import com.aboutme.domain.persistence.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, Long>
