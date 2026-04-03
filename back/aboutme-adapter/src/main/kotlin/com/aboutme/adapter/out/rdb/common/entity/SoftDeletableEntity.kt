package com.aboutme.adapter.out.rdb.common.entity

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class SoftDeletableEntity(id: Long? = null) : BaseEntity(id) {
    val deletedAt: LocalDateTime? = null
}
