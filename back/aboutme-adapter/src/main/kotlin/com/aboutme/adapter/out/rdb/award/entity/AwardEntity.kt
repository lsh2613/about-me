package com.aboutme.adapter.out.rdb.award.entity

import com.aboutme.adapter.out.rdb.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Table(name = "award")
@Entity
class AwardEntity(
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
