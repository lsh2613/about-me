package com.aboutme.adapter.out.rdb.certificate.mapper

import com.aboutme.adapter.out.rdb.certificate.entity.CertificateEntity
import com.aboutme.core.certificate.domain.Certificate

class CertificateMapper {
    companion object {
        fun toDomain(entity: CertificateEntity) =
            Certificate(
                id = entity.id,
                name = entity.name,
                issuer = entity.issuer,
                issueDate = entity.issueDate,
                expireDate = entity.expireDate,
                seq = entity.seq,
            )

        fun toEntity(certificate: Certificate) =
            CertificateEntity(
                name = certificate.name,
                issuer = certificate.issuer,
                issueDate = certificate.issueDate,
                expireDate = certificate.expireDate,
                seq = certificate.seq,
            )
    }
}
