package com.aboutme.app.award.service.dto.command

import java.time.LocalDate

data class AwardSyncCommand(
    val id: Long? = null,
    val name: String,
    val issuer: String,
    val issueDate: LocalDate,
    val description: String,
    val seq: Int,
)
