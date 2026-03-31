package com.aboutme.app.certificate.service.dto.command

import java.time.LocalDate

data class CertificateSyncCommand(
    val id: Long? = null,
    val name: String,
    val issuer: String,
    val issueDate: LocalDate,
    val expireDate: LocalDate,
    val seq: Int,
)
