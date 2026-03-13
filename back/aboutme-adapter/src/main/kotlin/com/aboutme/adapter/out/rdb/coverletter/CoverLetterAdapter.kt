package com.aboutme.adapter.out.rdb.coverletter

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.coverletter.mapper.CoverLetterEntityMapper
import com.aboutme.adapter.out.rdb.coverletter.repository.CoverLetterJpaRepository
import com.aboutme.app.coverletter.port.out.CoverLetterCommandPort
import com.aboutme.app.coverletter.port.out.CoverLetterQueryPort
import com.aboutme.core.coverletter.domain.CoverLetter

@Adapter
class CoverLetterAdapter(
    private val coverLetterJpaRepository: CoverLetterJpaRepository,
) : CoverLetterCommandPort, CoverLetterQueryPort {
    override fun loadAll(): List<CoverLetter> {
        return coverLetterJpaRepository.findAll()
            .map { CoverLetterEntityMapper.Companion.toDomain(it) }
    }
}
