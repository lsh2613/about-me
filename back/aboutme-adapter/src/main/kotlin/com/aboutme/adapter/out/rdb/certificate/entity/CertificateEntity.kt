package com.aboutme.adapter.out.rdb.certificate.entity

import com.aboutme.adapter.out.rdb.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Table(name = "certificate")
@Entity
class CertificateEntity(
    name: String,
    issuer: String,
    issueDate: LocalDate,
    expireDate: LocalDate,
    seq: Int,
) : BaseEntity() {
    var name: String = name
        protected set
    var issuer: String = issuer
        protected set
    var issueDate: LocalDate = issueDate
        protected set
    var expireDate: LocalDate = expireDate
        protected set
    var seq: Int = seq
        protected set

    fun update(
        name: String,
        issuer: String,
        issueDate: LocalDate,
        expireDate: LocalDate,
        seq: Int,
    ) {
        this.name = name
        this.issuer = issuer
        this.issueDate = issueDate
        this.expireDate = expireDate
        this.seq = seq
    }
}
