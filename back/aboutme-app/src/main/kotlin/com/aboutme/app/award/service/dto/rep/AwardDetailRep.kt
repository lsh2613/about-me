package com.aboutme.app.award.service.dto.rep

import com.aboutme.core.award.domain.Award
import java.time.LocalDate

data class AwardDetailRep(
    val id: Long,
    val name: String,
    val issuer: String,
    val issueDate: LocalDate,
    val description: String,
    val seq: Int,
) {
    companion object {
        fun of(award: Award): AwardDetailRep {
            return AwardDetailRep(
                id = award.id!!,
                name = award.name,
                issuer = award.issuer,
                issueDate = award.issueDate,
                description = award.description,
                seq = award.seq,
            )
        }
    }
}
