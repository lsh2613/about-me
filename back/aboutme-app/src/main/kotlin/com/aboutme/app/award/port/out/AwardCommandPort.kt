package com.aboutme.app.award.port.out

import com.aboutme.core.award.domain.Award

interface AwardCommandPort {
    fun deleteAll()

    fun delete(deleteIds: List<Long>)

    fun update(award: Award)

    fun save(award: Award)
}
