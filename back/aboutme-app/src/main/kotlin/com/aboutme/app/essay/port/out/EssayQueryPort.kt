package com.aboutme.app.essay.port.out

import com.aboutme.core.essay.domain.Essay

interface EssayQueryPort {
    fun findAll(): List<Essay>

    fun findOrThrow(id: Long): Essay
}
