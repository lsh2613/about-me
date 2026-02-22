package com.aboutme.domain.persistence.resume.entity

import com.aboutme.domain.persistence.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Table(name = "award")
@Entity
class Award(
    name: String,
    issuer: String,
    issueDate: LocalDate,
    description: String,
) : BaseEntity() {
    var name: String = name
        protected set
    var issuer: String = issuer
        protected set
    var issuedAt: LocalDate = issueDate
        protected set
    var description: String? = description
        protected set
}
