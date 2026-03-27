package com.aboutme.core.profile.domain

import com.aboutme.core.common.domain.Domain
import com.aboutme.core.profile.vo.Email

data class Profile(
    var name: String,
    var emails: List<Email>,
    var region: String,
    var education: String,
    var skills: List<String>,
    var profileImagePath: String? = null,
) : Domain() {
    fun update(
        name: String,
        emails: List<Email>,
        region: String,
        education: String,
        skills: List<String>,
    ) {
        this.name = name
        this.emails = emails
        this.region = region
        this.education = education
        this.skills = skills
    }
}
