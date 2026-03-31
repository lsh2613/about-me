package com.aboutme.core.certificate.domain

import java.time.LocalDate

data class Certificate(
    val id: Long? = null,
    var name: String,
    var issuer: String,
    var issueDate: LocalDate,
    var expireDate: LocalDate,
    var seq: Int,
) {
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
