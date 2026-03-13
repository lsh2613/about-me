package com.aboutme.adapter.out.rdb.certificate.entity

import com.aboutme.adapter.out.rdb.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "certificate")
@Entity
class CertificateEntity(
    name: String,
    issuer: String,
    issuedAt: LocalDateTime,
    expiredAt: LocalDateTime,
    orderNum: String,
) : BaseEntity() {
    var name: String = name
        protected set
    var issuer: String = issuer
        protected set
    var issuedAt: LocalDateTime = issuedAt
        protected set
    var expiredAt: LocalDateTime = expiredAt
        protected set
    var orderNum: String = orderNum
        protected set
}
