package com.aboutme.adapter.out.rdb.essay

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.essay.entity.EssayEntity
import com.aboutme.adapter.out.rdb.essay.mapper.EssayMapper
import com.aboutme.adapter.out.rdb.essay.repository.EssayJpaRepository
import com.aboutme.app.essay.port.out.EssayCommandPort
import com.aboutme.app.essay.port.out.EssayQueryPort
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.essay.domain.Essay
import com.aboutme.core.essay.error.EssayErrorCode
import org.springframework.data.repository.findByIdOrNull

@Adapter
class EssayAdapter(
    private val essayJpaRepository: EssayJpaRepository,
) : EssayCommandPort, EssayQueryPort {
    override fun save(essay: Essay) {
        essayJpaRepository.save(EssayMapper.toEntity(essay))
    }

    override fun findOrThrow(id: Long): Essay {
        return findEntityByIdOrThrow(id).let(EssayMapper::toDomain)
    }

    override fun findAll(): List<Essay> {
        return essayJpaRepository.findAll().map(EssayMapper::toDomain)
    }

    private fun findEntityByIdOrThrow(id: Long): EssayEntity {
        return essayJpaRepository.findByIdOrNull(id) ?: throw GlobalException(EssayErrorCode.NOT_FOUND)
    }

    override fun update(essay: Essay) {
        findEntityByIdOrThrow(essay.id!!).apply {
            update(
                title = essay.title,
                content = essay.content,
                seq = essay.seq,
            )
        }
    }

    override fun delete(ids: List<Long>) {
        essayJpaRepository.deleteAllById(ids)
    }

    override fun deleteAll() {
        essayJpaRepository.deleteAll()
    }
}
