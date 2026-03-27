package com.aboutme.app.profile.port.out

import com.aboutme.core.profile.domain.Profile

interface ProfileCommandPort {
    fun save(profile: Profile)

    fun update(profile: Profile)

    fun updateProfile(path: String)

    fun deleteProfile()
}
