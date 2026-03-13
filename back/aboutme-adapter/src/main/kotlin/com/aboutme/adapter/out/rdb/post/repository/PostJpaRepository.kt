package com.aboutme.adapter.out.rdb.post.repository

import com.aboutme.adapter.out.rdb.post.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<PostEntity, Long>
