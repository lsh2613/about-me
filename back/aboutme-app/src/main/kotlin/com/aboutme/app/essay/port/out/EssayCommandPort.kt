package com.aboutme.app.essay.port.out

import com.aboutme.core.essay.domain.Essay

interface EssayCommandPort {
    fun deleteAll()

    fun delete(ids: List<Long>)

    fun update(essay: Essay)

    fun save(essay: Essay)
}
