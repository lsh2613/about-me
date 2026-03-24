package com.aboutme.adapter.out.rdb.award.repository

import com.aboutme.adapter.out.rdb.award.entity.AwardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AwardJpaRepository : JpaRepository<AwardEntity, Long>
