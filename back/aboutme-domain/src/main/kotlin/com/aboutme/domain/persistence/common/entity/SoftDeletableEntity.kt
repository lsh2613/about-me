package com.aboutme.domain.persistence.common.entity

import com.aboutme.domain.persistence.common.entity.enum.EntityStatus
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class SoftDeletableEntity : BaseEntity() {
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private var status: EntityStatus = EntityStatus.ACTIVE

    fun active() {
        status = EntityStatus.ACTIVE
    }

    fun isActive(): Boolean {
        return status == EntityStatus.ACTIVE
    }

    fun delete() {
        status = EntityStatus.DELETED
    }

    fun isDeleted(): Boolean {
        return status == EntityStatus.DELETED
    }
}
