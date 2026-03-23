package com.aboutme.adapter.out.rdb.activity.repository

import com.aboutme.adapter.out.rdb.activity.entity.ActivityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ActivityJpaRepository : JpaRepository<ActivityEntity, Long>
