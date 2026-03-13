package com.aboutme.app.award.port.out

import com.aboutme.core.award.domain.Award

interface AwardQueryPort {
    fun loadAll(): List<Award>
}
