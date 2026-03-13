package com.aboutme.adapter.out.rdb.coverletter.mapper

import com.aboutme.adapter.out.rdb.coverletter.entity.CoverLetterEntity
import com.aboutme.core.coverletter.domain.CoverLetter

class CoverLetterEntityMapper {
    companion object {
        fun toDomain(entity: CoverLetterEntity): CoverLetter {
            return CoverLetter()
        }
    }
}
