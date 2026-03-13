package com.aboutme.adapter.out.rdb.award.mapper

import com.aboutme.adapter.out.rdb.award.entity.AwardEntity
import com.aboutme.core.award.domain.Award

class AwardEntityMapper {
    companion object {
        fun toDomain(entity: AwardEntity): Award {
            return Award()
        }
    }
}
