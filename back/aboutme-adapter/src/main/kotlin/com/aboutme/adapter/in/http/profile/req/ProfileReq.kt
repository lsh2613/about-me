package com.aboutme.adapter.`in`.http.profile.req

import com.aboutme.app.profile.service.dto.command.ProfileCommand
import com.aboutme.core.profile.vo.Email

data class ProfileReq(
    val name: String,
    val emails: List<String>,
    val region: String,
    val education: String,
    val skills: List<String>,
) {
    fun toCommand(): ProfileCommand =
        ProfileCommand(
            name = this.name,
            emails = this.emails.map { Email(it) },
            region = this.region,
            education = this.education,
            skills = this.skills,
        )
}
