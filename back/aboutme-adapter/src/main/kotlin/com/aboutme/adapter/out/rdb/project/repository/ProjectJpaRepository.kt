package com.aboutme.adapter.out.rdb.project.repository

import com.aboutme.adapter.out.rdb.project.entity.ProjectEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectJpaRepository : JpaRepository<ProjectEntity, Long>
