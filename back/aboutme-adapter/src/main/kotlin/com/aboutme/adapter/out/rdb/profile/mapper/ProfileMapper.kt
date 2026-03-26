package com.aboutme.adapter.out.rdb.profile.mapper

import com.aboutme.adapter.out.rdb.profile.entity.ProfileEntity
import com.aboutme.core.profile.domain.Profile
import com.aboutme.core.profile.vo.Email

class ProfileMapper {
    companion object {
        fun toDomain(entity: ProfileEntity): Profile =
            Profile(
                name = entity.name,
                emails = entity.emails.map { Email(it) },
                region = entity.region,
                education = entity.education,
                skills = entity.skills,
                profileImagePath = entity.profileImagePath,
            )

        fun toEntity(profile: Profile): ProfileEntity =
            ProfileEntity(
                name = profile.name,
                emails = profile.emails.map { it.value },
                region = profile.region,
                education = profile.education,
                skills = profile.skills,
                profileImagePath = profile.profileImagePath,
            )
    }
}
