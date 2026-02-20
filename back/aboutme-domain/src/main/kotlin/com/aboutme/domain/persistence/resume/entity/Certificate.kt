package com.aboutme.domain.persistence.resume.entity

import com.aboutme.domain.persistence.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "certificate")
@Entity
class Certificate(
    name: String,
    issuer: String,
    issuedAt: LocalDateTime,
    expiredAt: LocalDateTime,
    certificateNumber: String,
) : BaseEntity() {
    var name: String = name
        protected set
    var issuer: String = issuer
        protected set
    var issuedAt: LocalDateTime = issuedAt
        protected set
    var expiredAt: LocalDateTime = expiredAt
        protected set
    var certificateNumber: String = certificateNumber
        protected set
}
