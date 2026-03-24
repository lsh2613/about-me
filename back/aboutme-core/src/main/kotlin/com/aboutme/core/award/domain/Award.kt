package com.aboutme.core.award.domain

import com.aboutme.core.common.domain.Domain
import java.time.LocalDate

data class Award(
    var id: Long? = null,
    var name: String,
    var issuer: String,
    var issueDate: LocalDate,
    var description: String,
    var seq: Int,
) : Domain() {
    fun update(
        name: String,
        issuer: String,
        issueDate: LocalDate,
        description: String,
        seq: Int,
    ) {
        this.name = name
        this.issuer = issuer
        this.issueDate = issueDate
        this.description = description
        this.seq = seq
    }
}
