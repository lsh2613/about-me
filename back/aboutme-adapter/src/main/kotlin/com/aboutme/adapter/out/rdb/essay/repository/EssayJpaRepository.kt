package com.aboutme.adapter.out.rdb.essay.repository

import com.aboutme.adapter.out.rdb.essay.entity.EssayEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EssayJpaRepository : JpaRepository<EssayEntity, Long>
