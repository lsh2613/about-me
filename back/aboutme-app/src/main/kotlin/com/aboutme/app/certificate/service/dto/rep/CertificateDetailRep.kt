package com.aboutme.app.certificate.service.dto.rep

import com.aboutme.core.certificate.domain.Certificate
import java.time.LocalDate

data class CertificateDetailRep(
    val id: Long,
    val name: String,
    val issuer: String,
    val issueDate: LocalDate,
    val expireDate: LocalDate,
    val seq: Int,
) {
    companion object {
        fun of(certificate: Certificate) =
            CertificateDetailRep(
                id = certificate.id!!,
                name = certificate.name,
                issuer = certificate.issuer,
                issueDate = certificate.issueDate,
                expireDate = certificate.expireDate,
                seq = certificate.seq,
            )
    }
}
