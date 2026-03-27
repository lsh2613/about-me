package com.aboutme.adapter.out.rdb.profile.repository

import com.aboutme.adapter.out.rdb.profile.entity.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileJpaRepository : JpaRepository<ProfileEntity, Long>
