package com.aboutme.app.profile.service.dto.command

import com.aboutme.core.profile.domain.Profile
import com.aboutme.core.profile.vo.Email

data class ProfileCommand(
    val name: String,
    val emails: List<Email>,
    val region: String,
    val education: String,
    val skills: List<String>,
) {
    fun toDomain(): Profile =
        Profile(
            name = name,
            emails = emails,
            region = region,
            education = education,
            skills = skills,
        )
}
