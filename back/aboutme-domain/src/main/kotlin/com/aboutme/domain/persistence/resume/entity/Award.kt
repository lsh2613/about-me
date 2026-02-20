package com.aboutme.domain.persistence.resume.entity

import com.aboutme.domain.persistence.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "award")
@Entity
class Award(
    name: String,
    issuer: String,
    issuedAt: LocalDateTime,
    description: String,
) : BaseEntity() {
    var name: String = name
        protected set
    var issuer: String = issuer
        protected set
    var issuedAt: LocalDateTime = issuedAt
        protected set
    var description: String? = description
        protected set
}
