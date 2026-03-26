package com.aboutme.app.profile.port.out

import com.aboutme.core.profile.domain.Profile

interface ProfileQueryPort {
    fun findOrNull(): Profile?

    fun findOrThrow(): Profile
}
