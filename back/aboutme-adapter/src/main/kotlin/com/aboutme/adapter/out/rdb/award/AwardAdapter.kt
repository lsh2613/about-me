package com.aboutme.adapter.out.rdb.award

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.award.mapper.AwardEntityMapper
import com.aboutme.adapter.out.rdb.award.repository.AwardJpaRepository
import com.aboutme.app.award.port.out.AwardCommandPort
import com.aboutme.app.award.port.out.AwardQueryPort
import com.aboutme.core.award.domain.Award

@Adapter
class AwardAdapter(
    private val awardJpaRepository: AwardJpaRepository,
) : AwardCommandPort, AwardQueryPort {
    override fun loadAll(): List<Award> {
        return awardJpaRepository.findAll()
            .map { AwardEntityMapper.Companion.toDomain(it) }
    }
}
