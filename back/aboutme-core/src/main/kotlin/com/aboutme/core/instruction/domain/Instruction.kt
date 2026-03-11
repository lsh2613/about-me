package com.aboutme.core.instruction.domain

import com.aboutme.core.common.domain.Domain
import com.aboutme.core.instruction.vo.Email

data class Instruction(
    var name: String,
    var emails: List<Email>,
    var region: String,
    var education: String,
    var skills: List<String>,
    var profileImageUrl: String? = null,
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
