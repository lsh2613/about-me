package com.aboutme.app.essay.service.dto.command

data class EssaySyncCommand(
    val id: Long? = null,
    val title: String,
    val content: String,
    val seq: Int,
)
