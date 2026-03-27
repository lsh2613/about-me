package com.aboutme.app.profile.service.dto.rep

import com.aboutme.core.profile.domain.Profile

data class ProfileDetailRep(
    val name: String,
    val emails: List<String>,
    val region: String,
    val education: String,
    val skills: List<String>,
    val profileImageUrl: String?,
) {
    companion object {
        fun from(profile: Profile): ProfileDetailRep {
            return ProfileDetailRep(
                name = profile.name,
                emails = profile.emails.map { it.value },
                region = profile.region,
                education = profile.education,
                skills = profile.skills,
                profileImageUrl = profile.profileImagePath,
            )
        }
    }
}
