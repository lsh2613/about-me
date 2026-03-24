package com.aboutme.adapter.out.rdb.award

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.award.entity.AwardEntity
import com.aboutme.adapter.out.rdb.award.mapper.AwardMapper
import com.aboutme.adapter.out.rdb.award.repository.AwardJpaRepository
import com.aboutme.app.award.port.out.AwardCommandPort
import com.aboutme.app.award.port.out.AwardQueryPort
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.award.domain.Award
import com.aboutme.core.award.error.AwardErrorCode
import org.springframework.data.repository.findByIdOrNull

@Adapter
class AwardAdapter(
    private val awardJpaRepository: AwardJpaRepository,
) : AwardCommandPort, AwardQueryPort {
    override fun save(award: Award) {
        AwardMapper.toEntity(award).also {
            awardJpaRepository.save(it)
        }
    }

    override fun findOrThrow(id: Long): Award {
        return findEntityByIdOrThrow(id).let { AwardMapper.toDomain(it) }
    }

    override fun findAll(): List<Award> {
        return awardJpaRepository.findAll().map { AwardMapper.toDomain(it) }
    }

    private fun findEntityByIdOrThrow(id: Long): AwardEntity =
        awardJpaRepository.findByIdOrNull(id) ?: throw GlobalException(AwardErrorCode.NOT_FOUND)

    override fun update(award: Award) {
        findEntityByIdOrThrow(award.id!!).apply {
            update(
                name = award.name,
                issuer = award.issuer,
                issueDate = award.issueDate,
                description = award.description,
                seq = award.seq,
            )
        }
    }

    override fun delete(deleteIds: List<Long>) {
        awardJpaRepository.deleteAllById(deleteIds)
    }

    override fun deleteAll() {
        awardJpaRepository.deleteAll()
    }
}
