package com.aboutme.app.essay.service.dto.command

data class EssaySyncCommand(
    var id: Long?,
    var title: String,
    var content: String,
    var seq: Int,
)
